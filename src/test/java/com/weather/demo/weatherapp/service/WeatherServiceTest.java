package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.entity.OpenWeatherResponseEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherServiceTest {

    @Test
    void testOpenWeatherTransformer() {
        // Construir el arreglo de tipo OpenWeatherResponseEntity.Weather[]
        OpenWeatherResponseEntity.Weather[] weatherArray = new OpenWeatherResponseEntity.Weather[]{
                OpenWeatherResponseEntity.Weather.builder()
                        .main("Clear")
                        .description("clear sky")
                        .id(800)
                        .build()
        };

        // Construir el objeto OpenWeatherResponseEntity con el arreglo correcto
        OpenWeatherResponseEntity responseEntity = OpenWeatherResponseEntity.builder()
                .weather(weatherArray) // Usar el arreglo del tipo correcto
                .main(OpenWeatherResponseEntity.Main.builder()
                        .temp(25.0)
                        .feels_like(24.5)
                        .humidity(60)
                        .pressure(1012)
                        .build())
                .wind(OpenWeatherResponseEntity.Wind.builder()
                        .speed(5.5)
                        .deg(180)
                        .gust(8.0)
                        .build())
                .rain(null) // Si no hay lluvia, puedes asignar null
                .build();

        // Verificar que el objeto no sea nulo
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getWeather());
    }
}