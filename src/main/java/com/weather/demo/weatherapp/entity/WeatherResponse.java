package com.weather.demo.weatherapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WeatherResponse {
    private String weather;
    private String details;
    private String id; // ID de prueba
    private double temp; // Temperatura
    private double feels_like; // Sensación térmica

}
