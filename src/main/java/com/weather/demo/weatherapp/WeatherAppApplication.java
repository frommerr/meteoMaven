package com.weather.demo.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Habilita el soporte para tareas programadas
@SpringBootApplication
public class WeatherAppApplication {

	//TODO: Añadir condiciones extremas al viento y lluvia (COMPLETADO)
	//TODO: Arreglar el error con la consulta de lluvia (1h)
	//TODO: Añdir temperatura maxima y minima al dia
	//TODO: calculo personalizada de temperatura que se siente por radiacion solar

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

}
