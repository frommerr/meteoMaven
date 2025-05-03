package com.weather.demo.weatherapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase que representa la respuesta con información meteorológica formateada para el cliente
@Builder // Permite la creación de instancias usando el patrón Builder
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos, necesario para la serialización
@Setter // Genera métodos setter para todos los campos
@Getter // Genera métodos getter para todos los campos
public class WeatherResponse {
    // Condición meteorológica principal (por ejemplo: Rain, Snow, Clouds, Clear)
    private String weather;

    // Descripción detallada de la condición meteorológica
    private String details;

    // Identificador único del registro meteorológico
    private String id;

    // Temperatura actual en grados Celsius
    private double temp;

    // Temperatura percibida por el cuerpo humano en grados Celsius
    private double feels_like;

    // Porcentaje de humedad en el aire
    private int humidity;

    // Presión atmosférica en hPa (hectopascales)
    private int pressure;
}