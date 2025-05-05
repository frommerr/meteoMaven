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