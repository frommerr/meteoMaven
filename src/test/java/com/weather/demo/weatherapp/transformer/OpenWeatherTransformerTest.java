package com.weather.demo.weatherapp.transformer;

import com.weather.demo.weatherapp.entity.OpenWeatherResponseEntity;
import com.weather.demo.weatherapp.domain.CityWeather;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenWeatherTransformerTest {

    @Test
    void testTransformToDomain() {
        // Construir el arreglo de tipo OpenWeatherResponseEntity.Weather[]
        OpenWeatherResponseEntity.Weather[] weatherArray = new OpenWeatherResponseEntity.Weather[]{
                OpenWeatherResponseEntity.Weather.builder()
                        .main("Clear")
                        .description("clear sky")
                        .id(800)
                        .build()
        };

        // Construir el objeto OpenWeatherResponseEntity
        OpenWeatherResponseEntity responseEntity = OpenWeatherResponseEntity.builder()
                .weather(weatherArray)
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
                .rain(null) // Si no hay datos de lluvia
                .build();

        // Transformar usando el transformer
        OpenWeatherTransformer transformer = new OpenWeatherTransformer();
        CityWeather cityWeather = transformer.transformToDomain(responseEntity);

        // Validar los resultados
        assertEquals("Clear", cityWeather.getWeather());
        assertEquals("clear sky", cityWeather.getDetails());
        assertEquals("800", cityWeather.getId());
        assertEquals(25.0, cityWeather.getTemp());
        assertEquals(24.5, cityWeather.getFeels_like());
        assertEquals(60, cityWeather.getHumidity());
        assertEquals(1012, cityWeather.getPressure());
        assertEquals(5.5, cityWeather.getWindSpeed());
        assertEquals(180, cityWeather.getWindDeg());
        assertEquals(8.0, cityWeather.getWindGust());
    }
}