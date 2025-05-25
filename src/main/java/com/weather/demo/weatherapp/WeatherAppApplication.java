package com.weather.demo.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Habilita el soporte para tareas programadas
@SpringBootApplication
public class WeatherAppApplication {

    //TODO: Modificar la tabla de usuario: idioma y (location que ya esta añadido)

    public static void main(String[] args) {
        SpringApplication.run(WeatherAppApplication.class, args);
    }

}
