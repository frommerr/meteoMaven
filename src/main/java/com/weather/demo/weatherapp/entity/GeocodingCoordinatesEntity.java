package com.weather.demo.weatherapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase entidad que representa las coordenadas geográficas obtenidas de servicios de geocodificación
@Builder
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos, necesario para deserialización JSON
@Getter // Genera métodos getter para todos los campos
@Setter // Genera métodos setter para todos los campos
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propiedades JSON no mapeadas en esta clase
public class GeocodingCoordinatesEntity {

    // Latitud de las coordenadas geográficas, mapeada desde el campo "lat" del JSON
    @JsonProperty("lat")
    private String latitude;

    // Longitud de las coordenadas geográficas, mapeada desde el campo "lon" del JSON
    @JsonProperty("lon")
    private String longitude;
}