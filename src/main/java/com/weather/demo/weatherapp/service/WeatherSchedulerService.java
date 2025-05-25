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

/**
 * Servicio programado encargado de consultar periódicamente la API de clima,
 * detectar condiciones meteorológicas extremas y notificar a los usuarios registrados.
 */
@Service
public class WeatherSchedulerService {

    /**
     * Logger para registrar información y errores durante la ejecución de tareas programadas.
     */
    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedulerService.class);

    /**
     * Cliente HTTP para realizar peticiones a la API de clima.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Servicio para el envío de notificaciones SMS.
     */
    @Autowired
    private SmsNotificationService smsNotificationService;

    /**
     * Repositorio para acceder a los usuarios registrados.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * URL de la API de clima local utilizada para obtener los datos meteorológicos.
     */
    private static final String WEATHER_API_URL = "http://localhost:8080/api/v1/weather/jumilla";

    /**
     * Indica si se ha detectado una condición meteorológica extrema.
     */
    private boolean extremeConditionDetected = false;

    /**
     * Mensaje asociado a la condición extrema detectada.
     */
    private String extremeConditionMessage = "";

    /**
     * Marca de tiempo en la que se detectó la condición extrema.
     */
    private LocalDateTime extremeConditionTimestamp;

    /**
     * Pool de hilos para enviar notificaciones en paralelo.
     */
    private final ExecutorService notificationExecutor = Executors.newFixedThreadPool(10); // Puedes ajustar el tamaño según tus recursos

    /**
     * Tarea programada que consulta la API de clima cada 10 minutos y verifica condiciones extremas.
     */
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

    /**
     * Tarea programada que envía notificaciones de condiciones extremas cada 6 horas si se detectaron.
     */
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

    /**
     * Verifica si los datos meteorológicos contienen condiciones extremas y las registra si es necesario.
     *
     * @param weatherData Mapa con los datos meteorológicos actuales.
     */
    private void checkAndSetExtremeConditions(Map<String, Object> weatherData) {
        try {
            logger.info("Verificando condiciones extremas: {}", weatherData);
            detectExtremeTemperature(weatherData);
            detectExtremeWind(weatherData);
        } catch (Exception e) {
            logger.error("Error al verificar condiciones extremas: {}", e.getMessage(), e);
        }
    }

    /**
     * Detecta temperaturas extremas en los datos meteorológicos.
     *
     * @param weatherData Mapa con los datos meteorológicos actuales.
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
     * Detecta vientos extremos en los datos meteorológicos.
     *
     * @param weatherData Mapa con los datos meteorológicos actuales.
     */
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

    /**
     * Registra una condición extrema detectada.
     *
     * @param message Mensaje descriptivo de la condición extrema.
     */
    private void setExtremeCondition(String message) {
        extremeConditionDetected = true;
        extremeConditionMessage = message;
        extremeConditionTimestamp = LocalDateTime.now();
    }

    /**
     * Envía notificaciones SMS a todos los usuarios registrados usando hilos en paralelo.
     *
     * @param message Mensaje a enviar a los usuarios.
     */
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