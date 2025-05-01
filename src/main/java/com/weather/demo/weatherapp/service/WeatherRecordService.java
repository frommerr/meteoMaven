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
            record.setWeather((String) weatherData.get("weather"));
            record.setDetails((String) weatherData.get("details"));
            record.setWeatherId((String) weatherData.get("id"));
            record.setTemperature(Double.valueOf(weatherData.get("temp").toString()));
            record.setFeelsLike(Double.valueOf(weatherData.get("feels_like").toString()));
            record.setHumidity(Integer.valueOf(weatherData.get("humidity").toString()));
            record.setPressure(Integer.valueOf(weatherData.get("pressure").toString()));
            record.setConsultTimestamp(LocalDateTime.now());

            WeatherRecord savedRecord = repository.save(record);
            logger.info("Registro guardado exitosamente: {}", savedRecord);
            return savedRecord;
        } catch (Exception e) {
            logger.error("Error al guardar datos del clima: {}", e.getMessage(), e);
            throw e; // Re-lanzar la excepción para que pueda ser manejada a nivel superior si es necesario
        }
    }
}