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