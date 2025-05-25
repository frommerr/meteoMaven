package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la información meteorológica de una ciudad.
 * Incluye datos como el estado del clima, temperatura, humedad, presión y viento.
 */
@Builder
@Setter
@Getter
public class CityWeather {
    /**
     * Descripción general del clima (por ejemplo, "Clear", "Rain").
     */
    private String weather;

    /**
     * Detalles adicionales sobre el clima (por ejemplo, "cielo despejado").
     */
    private String details;

    /**
     * Identificador único del registro meteorológico o de la ciudad.
     */
    private String id;

    /**
     * Temperatura actual en grados Celsius.
     */
    private double temp;

    /**
     * Sensación térmica en grados Celsius.
     */
    private double feels_like;

    /**
     * Porcentaje de humedad relativa.
     */
    private int humidity;

    /**
     * Presión atmosférica en hPa.
     */
    private int pressure;

    /**
     * Velocidad del viento en metros por segundo.
     */
    private double windSpeed;

    /**
     * Dirección del viento en grados.
     */
    private int windDeg;

    /**
     * Ráfaga de viento máxima en metros por segundo.
     */
    private double windGust;

    /**
     * Cantidad de lluvia en la última hora (mm).
     */
    private double rain1h;
}