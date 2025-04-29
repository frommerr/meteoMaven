package com.weather.demo.weatherapp.controller;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.service.WeatherApiService;
import com.weather.demo.weatherapp.service.WeatherRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherRecordService weatherRecordService;

    @Autowired
    private WeatherApiService weatherApiService; // Servicio para obtener datos del clima

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWeather() {
        logger.info("Recibida solicitud de datos del clima");

        // Obtener datos del clima (externa o simulada)
        Map<String, Object> weatherData = weatherApiService.getCurrentWeather();

        // Guardar los datos en la base de datos
        WeatherRecord savedRecord = weatherRecordService.saveWeatherData(weatherData);
        logger.info("Datos del clima guardados con ID: {}", savedRecord.getId());

        return ResponseEntity.ok(weatherData);
    }
}