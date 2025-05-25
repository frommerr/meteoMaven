package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa un registro meteorológico almacenado en la base de datos.
 * Incluye información sobre el clima, temperatura, viento, lluvia y la fecha/hora de la consulta.
 */
@Entity
@Getter
@Setter
public class WeatherRecord {

    /**
     * Identificador único autogenerado para cada registro meteorológico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descripción general del clima (por ejemplo, "Clear", "Rain").
     */
    private String weather;

    /**
     * Detalles adicionales sobre el clima (por ejemplo, "cielo despejado").
     */
    private String details;

    /**
     * Identificador de la condición meteorológica.
     */
    private String weatherId;

    /**
     * Temperatura actual en grados Celsius.
     */
    private Double temperature;

    /**
     * Sensación térmica en grados Celsius.
     */
    private Double feelsLike;

    /**
     * Porcentaje de humedad relativa.
     */
    private Integer humidity;

    /**
     * Presión atmosférica en hPa.
     */
    private Integer pressure;

    /**
     * Velocidad del viento en metros por segundo.
     */
    private Double windSpeed;

    /**
     * Dirección del viento en grados.
     */
    private Integer windDeg;

    /**
     * Ráfaga máxima de viento en metros por segundo.
     */
    private Double windGust;

    /**
     * Cantidad de lluvia en la última hora (mm).
     */
    private Double rain1h;

    /**
     * Marca temporal que indica cuándo se realizó la consulta meteorológica.
     */
    private LocalDateTime consultTimestamp;
}