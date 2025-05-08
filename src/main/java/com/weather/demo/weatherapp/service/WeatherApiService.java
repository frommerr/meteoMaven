package com.weather.demo.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class WeatherApiService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.url}")
    private String weatherUrl;

    @Value("${api.key}")
    private String apiKey;

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