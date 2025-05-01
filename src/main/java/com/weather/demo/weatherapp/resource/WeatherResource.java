package com.weather.demo.weatherapp.resource;

import com.weather.demo.weatherapp.model.WeatherRecord;
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
    private WeatherRecordService weatherRecordService;

    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable("city") String city) {
        logger.info("Solicitud recibida para ciudad: {}", city);

        // Simulación de datos obtenidos de la API externa para la ciudad
        Map<String, Object> weatherData = Map.of(
                "weather", "Clouds",
                "details", "muy nuboso",
                "id", "803",
                "temp", 20.63,
                "feels_like", 20.52,
                "humidity", 68,
                "pressure", 1015
        );

        try {
            // Llamar al servicio para guardar los datos en la base de datos
            WeatherRecord savedRecord = weatherRecordService.saveWeatherData(weatherData);
            return ResponseEntity.ok(savedRecord);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Ocurrió un error al procesar la solicitud.");
        }
    }
}