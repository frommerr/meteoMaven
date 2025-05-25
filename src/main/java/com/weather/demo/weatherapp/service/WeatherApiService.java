package com.weather.demo.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Servicio encargado de consultar la API externa de clima para obtener información meteorológica actual de una ciudad.
 * Utiliza un {@link RestTemplate} para realizar peticiones HTTP y procesa la respuesta recibida.
 */
@Service
public class WeatherApiService {

    /**
     * Logger para registrar información y errores durante la consulta a la API de clima.
     */
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    /**
     * Cliente HTTP utilizado para realizar las peticiones a la API externa.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * URL base de la API de clima, configurada en las propiedades de la aplicación.
     */
    @Value("${weather.url}")
    private String weatherUrl;

    /**
     * Clave de acceso a la API de clima, configurada en las propiedades de la aplicación.
     */
    @Value("${api.key}")
    private String apiKey;

    /**
     * Obtiene la información meteorológica actual de una ciudad consultando la API externa.
     *
     * @param city Nombre de la ciudad para la cual se solicita la información meteorológica.
     * @return Un mapa con los datos meteorológicos obtenidos de la API.
     * @throws RuntimeException si ocurre un error al consultar la API.
     */
    public Map<String, Object> getCurrentWeather(String city) {
        try {
            String url = String.format("%s?q=%s&appid=%s&units=metric&lang=es", weatherUrl, city, apiKey);

            logger.info("Consultando API de clima: {}", url.replace(apiKey, "API_KEY"));

            Map<String, Object> apiResponse = restTemplate.getForObject(url, Map.class);
            logger.info("Respuesta recibida de la API con {} elementos", apiResponse != null ? apiResponse.size() : 0);

            if (apiResponse != null && apiResponse.containsKey("rain")) {
                Map<String, Object> rainData = (Map<String, Object>) apiResponse.get("rain");

                if (rainData.containsKey("1h")) {
                    logger.info("Volumen de lluvia en la última hora: {} mm", rainData.get("1h"));
                } else {
                    logger.warn("El campo '1h' no está presente en el objeto 'rain'.");
                }
            } else {
                logger.warn("No se encontraron datos de lluvia en la respuesta de la API.");
            }

            return apiResponse;
        } catch (Exception e) {
            logger.error("Error al obtener datos del clima: " + e.getMessage(), e);
            throw new RuntimeException("Error al consultar la API de clima.", e);
        }
    }
}