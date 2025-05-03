package com.weather.demo.weatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Clase de configuración que provee beans para la aplicación
@Configuration
public class AppConfig {

    // Crea un bean RestTemplate para realizar llamadas HTTP a APIs externas
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}