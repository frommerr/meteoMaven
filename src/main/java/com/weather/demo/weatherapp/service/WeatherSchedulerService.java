package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.User;
import com.weather.demo.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

// Servicio que programa y ejecuta consultas periódicas al servicio meteorológico
@Service
public class WeatherSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedulerService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsNotificationService smsNotificationService;

    @Autowired
    private UserRepository userRepository;

    private static final String WEATHER_API_URL = "http://localhost:8080/api/v1/weather/jumilla";

    private boolean extremeConditionDetected = false;
    private String extremeConditionMessage = "";
    private LocalDateTime extremeConditionTimestamp;

    /**
     * Consulta periódica a la API de clima para verificar condiciones extremas
     */
    @Scheduled(fixedRate = 600000) // Cada 10 minutos
    public void fetchWeatherData() {
        try {
            logger.info("Realizando llamada programada a la API de clima: {}", WEATHER_API_URL);

            Map<String, Object> weatherData = restTemplate.getForObject(WEATHER_API_URL, Map.class);

            if (weatherData != null) {
                logger.info("Datos obtenidos de la API: {}", weatherData);

                // Verificar condiciones extremas
                checkAndSetExtremeConditions(weatherData);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la llamada programada a la API: {}", e.getMessage(), e);
        }
    }

    /**
     * Envía notificaciones por SMS si se detectaron condiciones meteorológicas extremas
     */
    @Scheduled(cron = "0 0 */6 * * *") // Cada 6 horas
    public void sendExtremeWeatherNotifications() {
        if (extremeConditionDetected) {
            logger.info("Enviando notificaciones de condiciones extremas detectadas...");
            String formattedTimestamp = extremeConditionTimestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String messageWithTimestamp = extremeConditionMessage + " Detectado a las: " + formattedTimestamp;
            sendNotifications(messageWithTimestamp);
            extremeConditionDetected = false;
            extremeConditionMessage = "";
            extremeConditionTimestamp = null;
        } else {
            logger.info("No se detectaron condiciones extremas en las últimas 6 horas.");
        }
    }

    /**
     * Analiza los datos meteorológicos en busca de condiciones extremas
     */
    private void checkAndSetExtremeConditions(Map<String, Object> weatherData) {
        try {
            logger.info("Verificando condiciones extremas: {}", weatherData);

            // Verificar temperatura extrema
            detectExtremeTemperature(weatherData);

            // Verificar viento extremo
            detectExtremeWind(weatherData);

            // Verificar lluvia extrema
            detectExtremeRain(weatherData);

        } catch (Exception e) {
            logger.error("Error al verificar condiciones extremas: {}", e.getMessage(), e);
        }
    }

    /**
     * Detecta condiciones de temperatura extrema
     */
    private void detectExtremeTemperature(Map<String, Object> weatherData) {
        try {
            String temperatureStr = (String) weatherData.get("temperature");
            Double temperature = Double.parseDouble(temperatureStr);

            if (temperature > 40.0) {
                logger.info("Condición extrema detectada: temperatura alta ({} °C)", temperature);
                setExtremeCondition("Advertencia: Altas temperaturas en tu zona (" + temperature + "°C)");
            } else if (temperature < 0.0) {
                logger.info("Condición extrema detectada: temperatura baja ({} °C)", temperature);
                setExtremeCondition("Advertencia: Bajas temperaturas en tu zona (" + temperature + "°C)");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir la temperatura a Double: {}", e.getMessage(), e);
        }
    }

    /**
     * Detecta condiciones de viento extremo
     */
    private void detectExtremeWind(Map<String, Object> weatherData) {
        try {
            String windSpeedStr = (String) weatherData.get("windSpeed");
            Double windSpeed = Double.parseDouble(windSpeedStr);

            if (windSpeed > 80.0) { // Ejemplo: viento mayor a 80 km/h
                logger.info("Condición extrema detectada: viento fuerte ({} km/h)", windSpeed);
                setExtremeCondition("Advertencia: Fuertes vientos en tu zona (" + windSpeed + " km/h)");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir la velocidad del viento a Double: {}", e.getMessage(), e);
        }
    }

    /**
     * Detecta condiciones de lluvia extrema
     */
    private void detectExtremeRain(Map<String, Object> weatherData) {
        try {
            Object rainVolumeObj = weatherData.get("rain1h");

            // Verificar si el objeto es nulo o vacío
            if (rainVolumeObj == null || rainVolumeObj.toString().trim().isEmpty()) {
                logger.warn("El volumen de lluvia ('rain1h') está ausente o vacío en los datos meteorológicos.");
                return; // Salir del método si no hay datos válidos
            }

            // Convertir el valor a Double
            Double rainVolume = Double.parseDouble(rainVolumeObj.toString());

            // Detectar condiciones extremas de lluvia
            if (rainVolume > 50.0) { // Ejemplo: lluvia mayor a 50 mm en 1 hora
                logger.info("Condición extrema detectada: lluvia intensa ({} mm)", rainVolume);
                setExtremeCondition("Advertencia: Fuertes lluvias en tu zona (" + rainVolume + " mm en la última hora)");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir el volumen de lluvia ('rain1h') a Double: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al verificar condiciones de lluvia extrema: {}", e.getMessage(), e);
        }
    }

    /**
     * Configura las variables de estado para la condición extrema detectada
     */
    private void setExtremeCondition(String message) {
        extremeConditionDetected = true;
        extremeConditionMessage = message;
        extremeConditionTimestamp = LocalDateTime.now();
    }

    /**
     * Envía notificaciones SMS a todos los usuarios registrados
     */
    private void sendNotifications(String message) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            smsNotificationService.sendSms(user.getPhoneNumber(), message);
            logger.info("Notificación enviada a {}: {}", user.getPhoneNumber(), message);
        }
    }
}