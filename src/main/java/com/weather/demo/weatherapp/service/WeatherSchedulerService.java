package com.weather.demo.weatherapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedulerService.class);

    @Autowired
    private RestTemplate restTemplate;

    // URL de la API
    private static final String WEATHER_API_URL = "http://localhost:8080/api/v1/weather/jumilla";

    // Metodo programado que se ejecutará cada 10 minutos
    @Scheduled(fixedRate = 600000) // 10 minutos en milisegundos
    public void fetchWeatherData() {
        try {
            logger.info("Realizando llamada programada a la API de clima: {}", WEATHER_API_URL);

            // Realizar la llamada HTTP GET a la API
            ResponseEntity<String> response = restTemplate.getForEntity(WEATHER_API_URL, String.class);

            // Registrar la respuesta de la API
            logger.info("Respuesta de la API: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Error al realizar la llamada programada a la API: {}", e.getMessage(), e);
        }
    }
}