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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    // Pool de hilos para enviar mensajes en paralelo
    private final ExecutorService notificationExecutor = Executors.newFixedThreadPool(10); // Puedes ajustar el tamaño según tus recursos

    @Scheduled(fixedRate = 600000)
    public void fetchWeatherData() {
        try {
            logger.info("Realizando llamada programada a la API de clima: {}", WEATHER_API_URL);

            Map<String, Object> weatherData = restTemplate.getForObject(WEATHER_API_URL, Map.class);

            if (weatherData != null) {
                logger.info("Datos obtenidos de la API: {}", weatherData);
                checkAndSetExtremeConditions(weatherData);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la llamada programada a la API: {}", e.getMessage(), e);
        }
    }

    // cron de 6 horas: cron = "0 0 */6 * * *"
    // cron de 1 minuto: cron = "0 * * * * *"

    @Scheduled(cron = "0 0 2/6 * * *")
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

    private void checkAndSetExtremeConditions(Map<String, Object> weatherData) {
        try {
            logger.info("Verificando condiciones extremas: {}", weatherData);
            detectExtremeTemperature(weatherData);
            detectExtremeWind(weatherData);
            detectExtremeRain(weatherData);
        } catch (Exception e) {
            logger.error("Error al verificar condiciones extremas: {}", e.getMessage(), e);
        }
    }

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

    private void detectExtremeWind(Map<String, Object> weatherData) {
        try {
            String windSpeedStr = (String) weatherData.get("windSpeed");
            Double windSpeed = Double.parseDouble(windSpeedStr);

            if (windSpeed > 80.0) {
                logger.info("Condición extrema detectada: viento fuerte ({} km/h)", windSpeed);
                setExtremeCondition("Advertencia: Fuertes vientos en tu zona (" + windSpeed + " km/h)");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir la velocidad del viento a Double: {}", e.getMessage(), e);
        }
    }

    private void detectExtremeRain(Map<String, Object> weatherData) {
        try {
            if (!weatherData.containsKey("rain")) {
                logger.warn("El campo 'rain' está ausente en los datos meteorológicos.");
                return;
            }

            Map<String, Object> rainData = (Map<String, Object>) weatherData.get("rain");

            if (rainData == null || !rainData.containsKey("1h")) {
                logger.warn("El volumen de lluvia ('rain.1h') está ausente en los datos meteorológicos. Datos actuales de 'rain': {}", rainData);
                return;
            }

            Double rainVolume = Double.parseDouble(rainData.get("1h").toString());

            if (rainVolume > 50.0) {
                logger.info("Condición extrema detectada: lluvia intensa ({} mm)", rainVolume);
                setExtremeCondition("Advertencia: Fuertes lluvias en tu zona (" + rainVolume + " mm en la última hora)");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir el volumen de lluvia ('rain.1h') a Double: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al verificar condiciones de lluvia extrema: {}", e.getMessage(), e);
        }
    }

    private void setExtremeCondition(String message) {
        extremeConditionDetected = true;
        extremeConditionMessage = message;
        extremeConditionTimestamp = LocalDateTime.now();
    }

    // MODIFICADO: Ahora usa hilos para enviar notificaciones en paralelo
    private void sendNotifications(String message) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            notificationExecutor.submit(() -> {
                smsNotificationService.sendSms(user.getPhoneNumber(), message);
                logger.info("Notificación enviada a {}: {}", user.getPhoneNumber(), message);
            });
        }
    }

    // Si quieres liberar recursos cuando la app se cierre:
    // @PreDestroy
    // public void shutdownExecutor() {
    //     notificationExecutor.shutdown();
    // }
}