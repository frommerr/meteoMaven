package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.User;
import com.weather.demo.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime; // Importar para manejar timestamps
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

    private boolean extremeConditionDetected = false; // Indicador para condiciones extremas
    private String extremeConditionMessage = ""; // Mensaje específico de la condición extrema
    private LocalDateTime extremeConditionTimestamp; // Tiempo de detección de la condición extrema

    // Realiza la llamada a la API cada 10 minutos para verificar condiciones extremas
    @Scheduled(fixedRate = 600000) // 10 minutos en milisegundos
    public void fetchWeatherData() {
        try {
            logger.info("Realizando llamada programada a la API de clima: {}", WEATHER_API_URL);

            // Llamada HTTP a la API
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

    // Este metodo se ejecuta cada 6 horas para enviar notificaciones si se detectaron condiciones extremas
    @Scheduled(cron = "0 0 */6 * * *") // Cada 6 horas
    public void sendExtremeWeatherNotifications() {
        if (extremeConditionDetected) {
            logger.info("Enviando notificaciones de condiciones extremas detectadas...");
            String formattedTimestamp = extremeConditionTimestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String messageWithTimestamp = extremeConditionMessage + " Detectado a las: " + formattedTimestamp;
            sendNotifications(messageWithTimestamp);
            extremeConditionDetected = false; // Restablecer indicador después de enviar las notificaciones
            extremeConditionMessage = ""; // Limpiar el mensaje después de enviarlo
            extremeConditionTimestamp = null; // Limpiar el tiempo de detección
        } else {
            logger.info("No se detectaron condiciones extremas en las últimas 6 horas.");
        }
    }

    // Verifica si hay condiciones extremas y ajusta el indicador, mensaje y tiempo de detección
    private void checkAndSetExtremeConditions(Map<String, Object> weatherData) {
        try {
            logger.info("Verificando condiciones extremas: {}", weatherData);

            String temperatureStr = (String) weatherData.get("temperature");
            Double temperature = Double.parseDouble(temperatureStr);

            // Detectar condiciones extremas (ajustado para pruebas)
            if (temperature > 40.0) { // Cambia este umbral según los requisitos
                logger.info("Condición extrema detectada: temperatura alta ({} °C)", temperature);
                extremeConditionDetected = true;
                extremeConditionMessage = "Advertencia: Altas temperaturas en tu zona (" + temperature + "°C)";
                extremeConditionTimestamp = LocalDateTime.now(); // Capturar el tiempo actual
            } else if (temperature < 0.0) {
                logger.info("Condición extrema detectada: temperatura baja ({} °C)", temperature);
                extremeConditionDetected = true;
                extremeConditionMessage = "Advertencia: Bajas temperaturas en tu zona (" + temperature + "°C)";
                extremeConditionTimestamp = LocalDateTime.now(); // Capturar el tiempo actual
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir la temperatura a Double: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al verificar condiciones extremas: {}", e.getMessage(), e);
        }
    }

    // Envía notificaciones a todos los usuarios registrados
    private void sendNotifications(String message) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            smsNotificationService.sendSms(user.getPhoneNumber(), message);
            logger.info("Notificación enviada a {}: {}", user.getPhoneNumber(), message);
        }
    }
}