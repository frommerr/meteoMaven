package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CityWeather {
    private String weather;
    private String details;
    private String id; // ID de prueba
    private double temp; // Temperatura
    private double feels_like; // Sensación térmica

    private int humidity; // Humedad
    private int pressure; // rpesión atmosferica
}
