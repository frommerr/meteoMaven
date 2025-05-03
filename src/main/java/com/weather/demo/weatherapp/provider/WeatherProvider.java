package com.weather.demo.weatherapp.provider;

import com.weather.demo.weatherapp.domain.CityCoordinates;
import com.weather.demo.weatherapp.entity.OpenWeatherResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

// Servicio proveedor que se encarga de obtener datos meteorológicos actuales
// mediante llamadas a una API externa de clima basándose en coordenadas geográficas
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class WeatherProvider {

    // Clave de API necesaria para autenticarse con el servicio meteorológico externo
    @Value("${api.key}")
    private String apiKey;

    // URL base del servicio meteorológico que se utilizará para las consultas
    @Value("${weather.url}")
    private String weatherUrl;

    /**
     * Obtiene los datos meteorológicos actuales para las coordenadas geográficas especificadas
     *
     * @param cityCoordinates Objeto que contiene las coordenadas (latitud y longitud) de la ciudad
     * @return Entidad con la respuesta completa del servicio meteorológico
     * @throws Exception Si ocurre un error durante la comunicación con la API o si la respuesta es inválida
     */
    public OpenWeatherResponseEntity getWeather(final CityCoordinates cityCoordinates) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<OpenWeatherResponseEntity> responseEntity;

        // Preparamos el objeto de solicitud HTTP sin cuerpo ni cabeceras especiales
        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);

        // Construimos la URL para la API meteorológica con los parámetros necesarios
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(weatherUrl)
                .queryParam("lat", cityCoordinates.getLatitude()) // Latitud de la ubicación
                .queryParam("lon", cityCoordinates.getLongitude()) // Longitud de la ubicación
                .queryParam("appid", apiKey) // Incluimos la clave de API para autenticación
                .queryParam("units", "metric") // Utilizamos el sistema métrico para las medidas
                .queryParam("lang", "sp")     // Establecemos el idioma español para la respuesta
                .build();

        try {
            // Realizamos la petición HTTP GET al servicio meteorológico
            responseEntity = restTemplate
                    .exchange(uriBuilder.toUriString(),
                            HttpMethod.GET,
                            requestEntity,
                            OpenWeatherResponseEntity.class);
        } catch (HttpStatusCodeException e) {
            // Capturamos y relanzamos excepciones relacionadas con errores HTTP
            throw new Exception("Error while calling Weather API: " + e.getMessage(), e);
        }

        // Validamos que la respuesta contenga datos meteorológicos
        OpenWeatherResponseEntity responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.getWeather() == null || responseBody.getWeather().length == 0) {
            throw new Exception("Weather API returned null or empty response.");
        }

        // Devolvemos la respuesta completa del servicio meteorológico
        return responseBody;
    }
}