package com.weather.demo.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

            // Realizar la llamada a la API externa
            Map<String, Object> apiResponse = restTemplate.getForObject(url, Map.class);

            // Transformar la respuesta al formato esperado
            Map<String, Object> weatherData = new HashMap<>();

            // Extraer datos del JSON anidado que devuelve la API
            Map<String, Object> mainData = (Map<String, Object>) apiResponse.get("main");
            LinkedHashMap<String, Object> weatherArray =
                    (LinkedHashMap<String, Object>) ((java.util.ArrayList) apiResponse.get("weather")).get(0);

            // Mapear los datos al formato que espera nuestra aplicación
            weatherData.put("weather", weatherArray.get("main"));
            weatherData.put("details", weatherArray.get("description"));
            weatherData.put("id", weatherArray.get("id").toString());
            weatherData.put("temp", mainData.get("temp"));
            weatherData.put("feels_like", mainData.get("feels_like"));
            weatherData.put("humidity", mainData.get("humidity"));
            weatherData.put("pressure", mainData.get("pressure"));

            return weatherData;
        } catch (Exception e) {
            logger.error("Error al obtener datos del clima: " + e.getMessage(), e);
            return getFallbackWeatherData();
        }
    }

    private Map<String, Object> getFallbackWeatherData() {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("weather", "Clouds");
        fallback.put("details", "nubes dispersas");
        fallback.put("id", "802");
        fallback.put("temp", 16.5);
        fallback.put("feels_like", 15.8);
        fallback.put("humidity", 60);
        fallback.put("pressure", 1013);
        return fallback;
    }
}