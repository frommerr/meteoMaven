package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa las coordenadas geográficas de una ciudad.
 * Contiene la latitud y longitud en formato de cadena.
 */
@Builder
@Setter
@Getter
public class CityCoordinates {
    /**
     * Latitud de la ciudad en formato de cadena.
     */
    private String latitude;

    /**
     * Longitud de la ciudad en formato de cadena.
     */
    private String longitude;
}