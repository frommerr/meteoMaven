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

    @Autowired
    private WeatherApiService weatherApiService;

    @Autowired
    private WeatherRecordService weatherRecordService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWeather() {
        // Obtener datos del clima
        Map<String, Object> weatherData = weatherApiService.getCurrentWeather();

        // IMPORTANTE: Guardar los datos en la base de datos
        weatherRecordService.saveWeatherData(weatherData);

        return ResponseEntity.ok(weatherData);
    }
}