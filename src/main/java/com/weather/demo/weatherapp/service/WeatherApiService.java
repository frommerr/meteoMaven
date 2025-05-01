package com.weather.demo.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

    public Map<String, Object> getCurrentWeather() {
        try {
            // URL con la API key y los parámetros
            String url = weatherUrl + "?q=Madrid&appid=" + apiKey + "&units=metric&lang=es";

            logger.info("Consultando API de clima: {}", url.replace(apiKey, "API_KEY"));

            // Realizar la llamada a la API externa y devolver la respuesta completa
            Map<String, Object> apiResponse = restTemplate.getForObject(url, Map.class);
            logger.info("Respuesta recibida de la API con {} elementos", apiResponse.size());

            return apiResponse; // Devolver la respuesta sin transformar
        } catch (Exception e) {
            logger.error("Error al obtener datos del clima: " + e.getMessage(), e);
            return getFallbackWeatherData();
        }
    }

    private Map<String, Object> getFallbackWeatherData() {
        // Crear un objeto con la misma estructura que devuelve la API de OpenWeather
        Map<String, Object> fallback = new HashMap<>();

        // Datos de clima principal (main)
        Map<String, Object> main = new HashMap<>();
        main.put("temp", 16.5);
        main.put("feels_like", 15.8);
        main.put("humidity", 60);
        main.put("pressure", 1013);
        fallback.put("main", main);

        // Array weather
        List<Map<String, Object>> weatherList = new ArrayList<>();
        Map<String, Object> weather = new HashMap<>();
        weather.put("id", 802);
        weather.put("main", "Clouds");
        weather.put("description", "nubes dispersas");
        weather.put("icon", "03d");
        weatherList.add(weather);
        fallback.put("weather", weatherList);

        logger.info("Devolviendo datos de respaldo (fallback)");
        return fallback;
    }
}