package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// Clase que representa la información meteorológica de una ciudad
@Builder
@Setter
@Getter
public class CityWeather {
    // Condición general del clima (despejado, nublado, lluvia, etc.)
    private String weather;

    // Detalles adicionales sobre la condición meteorológica
    private String details;

    // Identificador único del registro meteorológico
    private String id;

    // Temperatura actual en grados Celsius
    private double temp;

    // Temperatura percibida por el cuerpo humano en grados Celsiu
    private double feels_like;

    // Porcentaje de humedad en el aire
    private int humidity;

    // Presión atmosférica en hPa (hectopascales)
    private int pressure;
}
