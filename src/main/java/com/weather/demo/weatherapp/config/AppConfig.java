package com.weather.demo.weatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración de Spring que define beans reutilizables para la aplicación.
 */
@Configuration
public class AppConfig {

    /**
     * Crea y expone un bean de {@link RestTemplate} para realizar llamadas HTTP a APIs externas.
     *
     * @return una instancia de RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}