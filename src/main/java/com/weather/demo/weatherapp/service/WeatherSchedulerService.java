package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.User;
import com.weather.demo.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class WeatherSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedulerService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsNotificationService smsNotificationService; // Servicio de mensajería

    @Autowired
    private UserRepository userRepository; // Repositorio para acceder a los usuarios

    private static final String WEATHER_API_URL = "http://localhost:8080/api/v1/weather/jumilla";

    // Automatización de llamadas a la API cada 10 minutos
    @Scheduled(fixedRate = 600000) // 10 minutos en milisegundos
    public void fetchWeatherData() {
        try {
            logger.info("Realizando llamada programada a la API de clima: {}", WEATHER_API_URL);

            // Llamada HTTP a la API
            Map<String, Object> weatherData = restTemplate.getForObject(WEATHER_API_URL, Map.class);

            if (weatherData != null) {
                // Registrar los datos del clima
                logger.info("Datos obtenidos de la API: {}", weatherData);

                // Verificar condiciones extremas y notificar
                checkAndNotifyExtremeConditions(weatherData);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la llamada programada a la API: {}", e.getMessage(), e);
        }
    }

    // Método para verificar condiciones extremas y enviar notificaciones
    private void checkAndNotifyExtremeConditions(Map<String, Object> weatherData) {
        try {
            // Extraer datos del mapa "main"
            Map<String, Object> mainData = (Map<String, Object>) weatherData.get("main");
            if (mainData != null) {
                Double temperature = (Double) mainData.get("temp");

                // Detectar condiciones extremas
                if (temperature > 40.0) {
                    sendNotifications("Advertencia: Altas temperaturas en tu zona (" + temperature + "ºC)");
                } else if (temperature < 0.0) {
                    sendNotifications("Advertencia: Bajas temperaturas en tu zona (" + temperature + "ºC)");
                }
            }
        } catch (Exception e) {
            logger.error("Error al verificar condiciones extremas: {}", e.getMessage(), e);
        }
    }

    // Método para enviar notificaciones a los usuarios
    private void sendNotifications(String message) {
        // Obtener usuarios registrados en la base de datos
        List<User> users = userRepository.findAll();

        // Enviar el mensaje a cada usuario
        for (User user : users) {
            smsNotificationService.sendSms(user.getPhoneNumber(), message);
            logger.info("Notificación enviada a {}: {}", user.getPhoneNumber(), message);
        }
    }
}