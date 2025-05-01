package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.repository.WeatherRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class WeatherRecordService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherRecordService.class);

    @Autowired
    private WeatherRecordRepository repository;

    public WeatherRecord saveWeatherData(Map<String, Object> weatherData) {
        logger.info("Guardando datos del clima: {}", weatherData);

        try {
            WeatherRecord record = new WeatherRecord();

            // Extraer el primer elemento del array "weather"
            var weatherArray = (java.util.List<Map<String, Object>>) weatherData.get("weather");
            if (weatherArray != null && !weatherArray.isEmpty()) {
                Map<String, Object> weatherDetails = weatherArray.get(0);
                record.setWeather((String) weatherDetails.getOrDefault("main", "Unknown")); // Ejemplo: "Clouds"
                record.setDetails((String) weatherDetails.getOrDefault("description", "No description")); // Ejemplo: "algo de nubes"
            } else {
                record.setWeather("Unknown");
                record.setDetails("No description");
            }

            // Convertir "id" a String si es necesario
            Object weatherId = weatherData.get("id");
            record.setWeatherId(weatherId != null ? weatherId.toString() : "N/A");

            // Extraer datos del mapa "main"
            var mainData = (Map<String, Object>) weatherData.get("main");
            if (mainData != null) {
                record.setTemperature(parseDouble(mainData.get("temp"), 0.0));
                record.setFeelsLike(parseDouble(mainData.get("feels_like"), 0.0));
                record.setHumidity(parseInteger(mainData.get("humidity"), 0));
                record.setPressure(parseInteger(mainData.get("pressure"), 0));
            } else {
                logger.warn("No se encontró la clave 'main' en los datos del clima.");
            }

            record.setConsultTimestamp(LocalDateTime.now());

            WeatherRecord savedRecord = repository.save(record);
            logger.info("Registro guardado exitosamente: {}", savedRecord);
            return savedRecord;
        } catch (Exception e) {
            logger.error("Error al guardar datos del clima: {}", e.getMessage(), e);
            throw e; // Re-lanzar la excepción para que pueda ser manejada a nivel superior si es necesario
        }
    }

    // Métodos auxiliares para manejar los valores y evitar NullPointerException
    private Double parseDouble(Object value, Double defaultValue) {
        try {
            return value != null ? Double.valueOf(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("No se pudo convertir el valor {} a Double. Usando valor predeterminado {}", value, defaultValue);
            return defaultValue;
        }
    }

    private Integer parseInteger(Object value, Integer defaultValue) {
        try {
            return value != null ? Integer.valueOf(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("No se pudo convertir el valor {} a Integer. Usando valor predeterminado {}", value, defaultValue);
            return defaultValue;
        }
    }
}