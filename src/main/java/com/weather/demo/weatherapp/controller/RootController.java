package com.weather.demo.weatherapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que gestiona la ruta raíz de la aplicación.
 * Proporciona un mensaje de bienvenida y orientación sobre el uso de la API.
 */
@RestController
public class RootController {

    /**
     * Maneja las peticiones GET a la ruta raíz ("/").
     *
     * @return Mensaje de bienvenida y guía de uso de la API.
     */
    @GetMapping("/")
    public String home() {
        return "Bienvenido a la API de WeatherApp. Usa /api/v1/weather/jumilla para consultar los datos meteorológicos.";
    }
}