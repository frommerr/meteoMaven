package com.weather.demo.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

// Servicio que se encarga de comunicarse con APIs externas de clima
// Proporciona métodos para consultar información meteorológica actual
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class WeatherApiService {

    // Configuración del logger para registrar eventos y errores durante la ejecución
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    // Cliente HTTP para realizar peticiones a la API externa
    @Autowired
    private RestTemplate restTemplate;

    // URL base del servicio de clima configurada en propiedades de la aplicación
    @Value("${weather.url}")
    private String weatherUrl;

    // Clave de acceso para la API de clima configurada en propiedades de la aplicación
    @Value("${api.key}")
    private String apiKey;

    /**
     * Obtiene datos meteorológicos actuales para una ciudad específica
     *
     * @param city Nombre de la ciudad para la cual se solicita información
     * @return Mapa con los datos meteorológicos obtenidos de la API externa
     * @throws RuntimeException si ocurre algún error en la comunicación con la API
     */
    public Map<String, Object> getCurrentWeather(String city) {
        try {
            // Construir la URL dinámica con la ciudad proporcionada
            String url = String.format("%s?q=%s&appid=%s&units=metric&lang=es", weatherUrl, city, apiKey);

            logger.info("Consultando API de clima: {}", url.replace(apiKey, "API_KEY"));

            // Realizar la llamada a la API externa y devolver la respuesta completa
            Map<String, Object> apiResponse = restTemplate.getForObject(url, Map.class);
            logger.info("Respuesta recibida de la API con {} elementos", apiResponse.size());

            return apiResponse; // Devolver la respuesta obtenida
        } catch (Exception e) {
            logger.error("Error al obtener datos del clima: " + e.getMessage(), e);
            throw new RuntimeException("Error al consultar la API de clima.", e);
        }
    }
}