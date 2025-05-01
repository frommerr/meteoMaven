package com.weather.demo.weatherapp.controller;

import com.weather.demo.weatherapp.service.WeatherApiService;
import com.weather.demo.weatherapp.service.WeatherRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherApiService weatherApiService;

    @Autowired
    private WeatherRecordService weatherRecordService;

    @GetMapping("/{city}")
    public ResponseEntity<Map<String, Object>> getWeather(@PathVariable("city") String city) {
        // Obtener datos del clima para la ciudad especificada
        Map<String, Object> weatherData = weatherApiService.getCurrentWeather(city);

        // Guardar los datos en la base de datos
        weatherRecordService.saveWeatherData(weatherData);

        return ResponseEntity.ok(weatherData);
    }
}