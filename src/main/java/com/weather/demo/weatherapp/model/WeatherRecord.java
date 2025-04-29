package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WeatherRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weather;
    private String details;
    private String weatherId; // Para evitar conflicto con el ID de la entidad
    private Double temperature;
    private Double feelsLike;
    private Integer humidity;
    private Integer pressure;
    private LocalDateTime consultTimestamp;

}
