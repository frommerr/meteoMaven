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
// Se encarga de detectar condiciones climáticas extremas y enviar alertas a usuarios
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class WeatherSchedulerService {

    // Configuración del logger para registrar eventos y errores durante la ejecución
    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedulerService.class);

    // Cliente HTTP para realizar peticiones al API de clima
    @Autowired
    private RestTemplate restTemplate;

    // Servicio para enviar notificaciones SMS a los usuarios
    @Autowired
    private SmsNotificationService smsNotificationService;

    // Repositorio para acceder a la información de usuarios registrados
    @Autowired
    private UserRepository userRepository;

    // URL del endpoint local que proporciona información meteorológica
    private static final String WEATHER_API_URL = "http://localhost:8080/api/v1/weather/jumilla";

    // Estado que indica si se ha detectado alguna condición meteorológica extrema
    private boolean extremeConditionDetected = false;

    // Mensaje descriptivo sobre la condición extrema detectada
    private String extremeConditionMessage = "";

    // Momento exacto en que se detectó la condición extrema
    private LocalDateTime extremeConditionTimestamp;

    /**
     * Consulta periódica a la API de clima para verificar condiciones extremas
     * Se ejecuta automáticamente cada 10 minutos
     */
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

    /**
     * Envía notificaciones por SMS si se detectaron condiciones meteorológicas extremas
     * Se ejecuta automáticamente cada 6 horas
     */
    @Scheduled(cron = "0 0 */6 * * *") // Cada 6 horas
    public void sendExtremeWeatherNotifications() {
        if (extremeConditionDetected) {
            logger.info("Enviando notificaciones de condiciones extremas detectadas...");
            String formattedTimestamp = extremeConditionTimestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String messageWithTimestamp = extremeConditionMessage + " Detectado a las: " + formattedTimestamp;
            sendNotifications(messageWithTimestamp);
            extremeConditionDetected = false; // Restablecer indicador
            extremeConditionMessage = ""; // Limpiar mensaje
            extremeConditionTimestamp = null; // Limpiar timestamp
        } else {
            logger.info("No se detectaron condiciones extremas en las últimas 6 horas.");
        }
    }

    /**
     * Analiza los datos meteorológicos en busca de condiciones extremas
     * Establece banderas y mensajes cuando detecta situaciones de alerta
     */
    private void checkAndSetExtremeConditions(Map<String, Object> weatherData) {
        try {
            logger.info("Verificando condiciones extremas: {}", weatherData);

            String temperatureStr = (String) weatherData.get("temperature");
            Double temperature = Double.parseDouble(temperatureStr);

            // Detectar condiciones extremas por temperatura
            if (temperature > 40.0) {
                logger.info("Condición extrema detectada: temperatura alta ({} °C)", temperature);
                extremeConditionDetected = true;
                extremeConditionMessage = "Advertencia: Altas temperaturas en tu zona (" + temperature + "°C)";
                extremeConditionTimestamp = LocalDateTime.now();
            } else if (temperature < 0.0) {
                logger.info("Condición extrema detectada: temperatura baja ({} °C)", temperature);
                extremeConditionDetected = true;
                extremeConditionMessage = "Advertencia: Bajas temperaturas en tu zona (" + temperature + "°C)";
                extremeConditionTimestamp = LocalDateTime.now();
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir la temperatura a Double: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al verificar condiciones extremas: {}", e.getMessage(), e);
        }
    }

    /**
     * Envía notificaciones SMS a todos los usuarios registrados en el sistema
     *
     * @param message Mensaje de alerta a enviar a los usuarios
     */
    private void sendNotifications(String message) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            smsNotificationService.sendSms(user.getPhoneNumber(), message);
            logger.info("Notificación enviada a {}: {}", user.getPhoneNumber(), message);
        }
    }
}