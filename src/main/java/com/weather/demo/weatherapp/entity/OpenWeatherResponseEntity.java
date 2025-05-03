package com.weather.demo.weatherapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase entidad que representa la respuesta de la API OpenWeather
@Builder // Permite la creación de instancias usando el patrón Builder
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos, necesario para deserialización JSON
@Getter // Genera métodos getter para todos los campos
@Setter // Genera métodos setter para todos los campos
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propiedades JSON no mapeadas en esta clase
public class OpenWeatherResponseEntity {
    // Array con información sobre las condiciones meteorológicas (descripción, icono, etc.)
    @JsonProperty("weather")
    private WeatherEntity[] weather;

    // Objeto que contiene los datos principales del clima (temperatura, humedad, presión)
    @JsonProperty("main")
    private Main main;

    // Clase interna que representa los datos principales de las condiciones meteorológicas
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Main {
        // Temperatura actual en grados Celsius
        @JsonProperty("temp")
        private double temp;

        // Temperatura percibida por el cuerpo humano en grados Celsius
        @JsonProperty("feels_like")
        private double feels_like;

        // Porcentaje de humedad en el aire
        @JsonProperty("humidity")
        private int humidity;

        // Presión atmosférica en hPa (hectopascales)
        @JsonProperty("pressure")
        private int pressure;
    }
}