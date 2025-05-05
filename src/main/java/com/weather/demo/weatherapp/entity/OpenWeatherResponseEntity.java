package com.weather.demo.weatherapp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpenWeatherResponseEntity {
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Rain rain;

    @Getter
    @Setter
    @Builder
    public static class Weather {
        private String main;
        private String description;
        private int id;
    }

    @Getter
    @Setter
    @Builder
    public static class Main {
        private double temp;
        private double feels_like;
        private int humidity;
        private int pressure;
    }

    @Getter
    @Setter
    @Builder
    public static class Wind {
        private double speed;
        private int deg;
        private Double gust;
    }

    @Getter
    @Setter
    @Builder
    public static class Rain {
        private Double _1h;
    }
}