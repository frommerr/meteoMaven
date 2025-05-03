package com.weather.demo.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// Clase que representa las coordenadas geográficas de una ciudad
@Builder
@Setter
@Getter
public class CityCoordinates {
    // Latitud de la ciudad en formato de cadena
    private String latitude;

    // Longitud de la ciudad en formato de cadena
    private String longitude;
}
