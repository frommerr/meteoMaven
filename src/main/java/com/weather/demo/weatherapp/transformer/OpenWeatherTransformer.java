package com.weather.demo.weatherapp.transformer;

import com.weather.demo.weatherapp.domain.CityWeather;
import com.weather.demo.weatherapp.entity.OpenWeatherResponseEntity;
import com.weather.demo.weatherapp.entity.WeatherResponse;
import org.springframework.stereotype.Service;

// Transformador que convierte datos meteorológicos entre formatos de OpenWeather y objetos del dominio
// Proporciona métodos para transformar la respuesta de la API externa a objetos internos y viceversa
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class OpenWeatherTransformer {

    /**
     * Transforma la respuesta de la API externa a un objeto del dominio
     *
     * @param entity Entidad que contiene la respuesta de OpenWeather
     * @return Objeto CityWeather con los datos meteorológicos procesados
     */
    public CityWeather transformToDomain(final OpenWeatherResponseEntity entity) {
        return CityWeather.builder()
                .weather(entity.getWeather()[0].getMain())        // Condición meteorológica principal
                .details(entity.getWeather()[0].getDescription()) // Descripción detallada
                .id(entity.getWeather()[0].getId())               // Identificador de la condición
                .temp(entity.getMain().getTemp())                 // Temperatura actual
                .feels_like(entity.getMain().getFeels_like())     // Sensación térmica
                .humidity(entity.getMain().getHumidity())         // Porcentaje de humedad
                .pressure(entity.getMain().getPressure())         // Presión atmosférica
                .build();
    }

    /**
     * Transforma un objeto del dominio a una entidad de respuesta API
     *
     * @param cityWeather Objeto del dominio con datos meteorológicos
     * @return Objeto WeatherResponse listo para ser devuelto por la API
     */
    public WeatherResponse transformToEntity(final CityWeather cityWeather) {
        return WeatherResponse.builder()
                .weather(cityWeather.getWeather())       // Condición meteorológica principal
                .details(cityWeather.getDetails())       // Descripción detallada
                .id(cityWeather.getId())                 // Identificador de la condición
                .temp(cityWeather.getTemp())             // Temperatura actual
                .feels_like(cityWeather.getFeels_like()) // Sensación térmica
                .humidity(cityWeather.getHumidity())     // Porcentaje de humedad
                .pressure(cityWeather.getPressure())     // Presión atmosférica
                .build();
    }
}