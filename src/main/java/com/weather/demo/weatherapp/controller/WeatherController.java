package com.weather.demo.weatherapp.controller;

import com.weather.demo.weatherapp.service.WeatherApiService;
import com.weather.demo.weatherapp.service.WeatherRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Controlador REST que maneja las peticiones relacionadas con datos meteorológicos
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    // Servicio para consultar la API externa de meteorología
    @Autowired
    private WeatherApiService weatherApiService;

    // Servicio para persistir los registros meteorológicos en la base de datos
    @Autowired
    private WeatherRecordService weatherRecordService;

    // Endpoint que obtiene y almacena datos meteorológicos para la ciudad especificada
    @GetMapping("/{city}")
    public ResponseEntity<Map<String, Object>> getWeather(@PathVariable("city") String city) {
        // Obtiene datos del clima para la ciudad especificada
        Map<String, Object> weatherData = weatherApiService.getCurrentWeather(city);

        // Guarda los datos en la base de datos
        weatherRecordService.saveWeatherData(weatherData);

        return ResponseEntity.ok(weatherData);
    }
}