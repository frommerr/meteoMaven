package com.weather.demo.weatherapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa las coordenadas geográficas obtenidas de servicios de geocodificación.
 * Utilizada para mapear la respuesta JSON de servicios externos que proporcionan latitud y longitud.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingCoordinatesEntity {

    /**
     * Latitud de las coordenadas geográficas, mapeada desde el campo "lat" del JSON.
     */
    @JsonProperty("lat")
    private String latitude;

    /**
     * Longitud de las coordenadas geográficas, mapeada desde el campo "lon" del JSON.
     */
    @JsonProperty("lon")
    private String longitude;
}