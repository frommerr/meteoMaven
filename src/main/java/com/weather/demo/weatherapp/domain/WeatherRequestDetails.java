package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Encapsula los detalles de una solicitud de información meteorológica.
 * Actualmente, solo contiene el nombre de la ciudad para la cual se solicita la información.
 */
@Builder
@Setter
@Getter
public class WeatherRequestDetails {
    /**
     * Nombre de la ciudad para la cual se solicita información meteorológica.
     */
    private String city;
}