package com.weather.demo.weatherapp.controller;

import com.weather.demo.weatherapp.service.WeatherApiService;
import com.weather.demo.weatherapp.service.WeatherRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST que gestiona las peticiones relacionadas con datos meteorológicos.
 * Permite obtener y almacenar información del clima para una ciudad específica.
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    /**
     * Servicio para consultar la API externa de meteorología.
     */
    @Autowired
    private WeatherApiService weatherApiService;

    /**
     * Servicio para persistir los registros meteorológicos en la base de datos.
     */
    @Autowired
    private WeatherRecordService weatherRecordService;

    /**
     * Endpoint que obtiene y almacena datos meteorológicos para la ciudad especificada.
     *
     * @param city Nombre de la ciudad para la que se solicita la información meteorológica.
     * @return Respuesta HTTP con los datos meteorológicos obtenidos.
     */
    @GetMapping("/{city}")
    public ResponseEntity<Map<String, Object>> getWeather(@PathVariable("city") String city) {
        // Obtiene datos del clima para la ciudad especificada
        Map<String, Object> weatherData = weatherApiService.getCurrentWeather(city);

        // Guarda los datos en la base de datos
        weatherRecordService.saveWeatherData(weatherData);

        return ResponseEntity.ok(weatherData);
    }
}