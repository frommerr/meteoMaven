package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// Clase que encapsula los detalles de una solicitud de información meteorológica
@Builder
@Setter
@Getter
public class WeatherRequestDetails {
    // Nombre de la ciudad para la cual se solicita información meteorológica
    private String city;
}