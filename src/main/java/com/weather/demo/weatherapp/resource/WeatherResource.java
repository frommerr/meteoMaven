package com.weather.demo.weatherapp.resource;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.service.WeatherApiService;
import com.weather.demo.weatherapp.service.WeatherRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherResource {

    private static final Logger logger = LoggerFactory.getLogger(WeatherResource.class);

    @Autowired
    private WeatherApiService weatherApiService;

    @Autowired
    private WeatherRecordService weatherRecordService;

    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable("city") String city) {
        logger.info("Solicitud recibida para ciudad: {}", city);

        try {
            Map<String, Object> weatherData = weatherApiService.getCurrentWeather(city);
            WeatherRecord savedRecord = weatherRecordService.saveWeatherData(weatherData);
            return ResponseEntity.ok(savedRecord);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Ocurrió un error al procesar la solicitud.");
        }
    }
}