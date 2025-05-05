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
    private String id;
    private double temp;
    private double feels_like;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private int windDeg;
    private double windGust;
    private double rain1h;
}