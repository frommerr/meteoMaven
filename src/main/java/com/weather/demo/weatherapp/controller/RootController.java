package com.weather.demo.weatherapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "Bienvenido a la API de WeatherApp. Usa /api/v1/weather/jumilla para consultar los datos meteorológicos.";
    }
}