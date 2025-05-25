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

/**
 * Servicio proveedor encargado de obtener información meteorológica
 * a partir de coordenadas geográficas, utilizando una API externa de clima.
 */
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class WeatherProvider {

    /**
     * Clave de API necesaria para autenticarse con el servicio de clima externo.
     */
    @Value("${api.key}")
    private String apiKey;

    /**
     * URL base del servicio de clima que se utilizará para las consultas.
     */
    @Value("${weather.url}")
    private String weatherUrl;

    /**
     * Obtiene la información meteorológica de una ciudad a partir de sus coordenadas.
     *
     * @param cityCoordinates Objeto que contiene la latitud y longitud de la ciudad.
     * @return Entidad con la respuesta de la API de clima.
     * @throws Exception Si ocurre un error durante la comunicación con la API o si la respuesta es inválida.
     */
    public OpenWeatherResponseEntity getWeather(final CityCoordinates cityCoordinates) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<OpenWeatherResponseEntity> responseEntity;

        // Preparamos el objeto de solicitud HTTP sin cuerpo ni cabeceras especiales
        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);

        // Construimos la URL para la API de clima con los parámetros necesarios
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(weatherUrl)
                .queryParam("lat", cityCoordinates.getLatitude()) // Latitud de la ciudad
                .queryParam("lon", cityCoordinates.getLongitude()) // Longitud de la ciudad
                .queryParam("appid", apiKey) // Clave de API para autenticación
                .queryParam("units", "metric") // Unidades métricas (Celsius)
                .queryParam("lang", "sp") // Idioma español
                .build();

        try {
            // Realizamos la petición HTTP GET al servicio de clima
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

        // Devolvemos la respuesta de la API de clima
        return responseBody;
    }
}