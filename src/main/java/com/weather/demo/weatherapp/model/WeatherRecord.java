package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Clase entidad que almacena registros históricos de condiciones meteorológicas consultadas
@Entity // Define esta clase como entidad persistible en la base de datos
@Getter // Genera automáticamente métodos getter para todos los campos
@Setter // Genera automáticamente métodos setter para todos los campos
public class WeatherRecord {

    // Identificador único autogenerado para cada registro meteorológico
    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática incremental de valores
    private Long id;

    // Condición meteorológica principal (por ejemplo: Rain, Snow, Clouds, Clear)
    private String weather;

    // Descripción detallada de la condición meteorológica
    private String details;

    // Identificador original de la condición meteorológica en la API externa
    private String weatherId; // Para evitar conflicto con el ID de la entidad

    // Temperatura registrada en grados Celsius
    private Double temperature;

    // Temperatura percibida por el cuerpo humano en grados Celsius
    private Double feelsLike;

    // Porcentaje de humedad en el aire
    private Integer humidity;

    // Presión atmosférica en hPa (hectopascales)
    private Integer pressure;

    // Fecha y hora en que se realizó la consulta meteorológica
    private LocalDateTime consultTimestamp;
}