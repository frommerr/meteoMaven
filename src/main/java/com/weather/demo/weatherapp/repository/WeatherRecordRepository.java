package com.weather.demo.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.weather.demo.weatherapp.model.WeatherRecord;

public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {
}