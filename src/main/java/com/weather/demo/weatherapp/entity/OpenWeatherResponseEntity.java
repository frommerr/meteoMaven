package com.weather.demo.weatherapp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa la respuesta de la API de OpenWeather.
 * Contiene información sobre el clima, condiciones principales, viento y lluvia.
 */
@Getter
@Setter
@Builder
public class OpenWeatherResponseEntity {
    /**
     * Array de condiciones meteorológicas actuales (por ejemplo, lluvia, nubes).
     */
    private Weather[] weather;

    /**
     * Información principal sobre temperatura, sensación térmica, humedad y presión.
     */
    private Main main;

    /**
     * Información sobre el viento, incluyendo velocidad, dirección y ráfagas.
     */
    private Wind wind;

    /**
     * Información sobre la lluvia en la última hora.
     */
    private Rain rain;

    /**
     * Representa una condición meteorológica específica.
     */
    @Getter
    @Setter
    @Builder
    public static class Weather {
        /**
         * Estado general del clima (por ejemplo, "Rain", "Clear").
         */
        private String main;

        /**
         * Descripción detallada del clima (por ejemplo, "lluvia ligera").
         */
        private String description;

        /**
         * Identificador único de la condición meteorológica.
         */
        private int id;
    }

    /**
     * Contiene los datos principales de la respuesta meteorológica.
     */
    @Getter
    @Setter
    @Builder
    public static class Main {
        /**
         * Temperatura actual en grados Celsius.
         */
        private double temp;

        /**
         * Sensación térmica en grados Celsius.
         */
        private double feels_like;

        /**
         * Porcentaje de humedad relativa.
         */
        private int humidity;

        /**
         * Presión atmosférica en hPa.
         */
        private int pressure;
    }

    /**
     * Información sobre el viento.
     */
    @Getter
    @Setter
    @Builder
    public static class Wind {
        /**
         * Velocidad del viento en metros por segundo.
         */
        private double speed;

        /**
         * Dirección del viento en grados.
         */
        private int deg;

        /**
         * Ráfaga máxima de viento en metros por segundo (puede ser nula).
         */
        private Double gust;
    }

    /**
     * Información sobre la lluvia.
     */
    @Getter
    @Setter
    @Builder
    public static class Rain {
        /**
         * Cantidad de lluvia en la última hora (mm), mapeada desde el campo "1h" del JSON.
         */
        private Double oneHour;
    }
}