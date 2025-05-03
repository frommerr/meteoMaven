package com.weather.demo.weatherapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase entidad que representa las condiciones meteorológicas específicas dentro de la respuesta de la API OpenWeather
@Builder // Permite la creación de instancias usando el patrón Builder
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos, necesario para deserialización JSON
@Getter // Genera métodos getter para todos los campos
@Setter // Genera métodos setter para todos los campos
public class WeatherEntity {
    // Identificador único de la condición meteorológica
    @JsonProperty("id")
    private String id;

    // Condición meteorológica principal (por ejemplo: Rain, Snow, Clouds, Clear)
    @JsonProperty("main")
    private String main;

    // Descripción detallada de la condición meteorológica
    @JsonProperty("description")
    private String description;

    // Código del icono que representa visualmente la condición meteorológica
    @JsonProperty("icon")
    private String icon;
}