package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.repository.WeatherRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class WeatherRecordService {

    @Autowired
    private WeatherRecordRepository repository;

    public WeatherRecord saveWeatherData(Map<String, Object> weatherData) {
        WeatherRecord record = new WeatherRecord();
        record.setWeather((String) weatherData.get("weather"));
        record.setDetails((String) weatherData.get("details"));
        record.setWeatherId((String) weatherData.get("id"));
        record.setTemperature(Double.valueOf(weatherData.get("temp").toString()));
        record.setFeelsLike(Double.valueOf(weatherData.get("feels_like").toString()));
        record.setHumidity(Integer.valueOf(weatherData.get("humidity").toString()));
        record.setPressure(Integer.valueOf(weatherData.get("pressure").toString()));
        record.setConsultTimestamp(LocalDateTime.now());

        return repository.save(record);
    }
}