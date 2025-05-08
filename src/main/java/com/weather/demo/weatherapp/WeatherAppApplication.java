package com.weather.demo.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Habilita el soporte para tareas programadas
@SpringBootApplication
public class WeatherAppApplication {

	//TODO: Implementar hilos para mensajeria a los usuarios (Opcional)

	//TODO: Meter campo de contraseña a la base de datos sel usuario
	//TODO: Dar acceso al Aiven a Ivan

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

}
