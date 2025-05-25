package com.weather.demo.weatherapp.provider;

import com.weather.demo.weatherapp.domain.WeatherRequestDetails;
import com.weather.demo.weatherapp.entity.GeocodingCoordinatesEntity;
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

/**
 * Servicio proveedor encargado de obtener coordenadas geográficas (latitud y longitud)
 * a partir del nombre de una ciudad, utilizando una API externa de geocodificación.
 */
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class GeocodingProvider {

    /**
     * Clave de API necesaria para autenticarse con el servicio de geocodificación externo.
     */
    @Value("${api.key}")
    private String apiKey;

    /**
     * URL base del servicio de geocodificación que se utilizará para las consultas.
     */
    @Value("${geocoding.url}")
    private String geocodingUrl;

    /**
     * Obtiene las coordenadas geográficas (latitud y longitud) a partir del nombre de una ciudad.
     *
     * @param weatherRequestDetails Objeto que contiene los detalles de la solicitud, incluido el nombre de la ciudad.
     * @return Entidad con las coordenadas geográficas de la ciudad especificada.
     * @throws Exception Si ocurre un error durante la comunicación con la API o si la respuesta es inválida.
     */
    public GeocodingCoordinatesEntity getCoordinates(final WeatherRequestDetails weatherRequestDetails) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<GeocodingCoordinatesEntity[]> responseEntity;

        // Preparamos el objeto de solicitud HTTP sin cuerpo ni cabeceras especiales
        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);

        // Construimos la URL para la API de geocodificación con los parámetros necesarios
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(geocodingUrl)
                .queryParam("q", weatherRequestDetails.getCity()) // Nombre de la ciudad a buscar
                .queryParam("limit", "1") // Limitamos a un solo resultado (el más relevante)
                .queryParam("appid", apiKey) // Incluimos la clave de API para autenticación
                .build();

        try {
            // Realizamos la petición HTTP GET al servicio de geocodificación
            responseEntity = restTemplate
                    .exchange(uriBuilder.toUriString(),
                            HttpMethod.GET,
                            requestEntity,
                            GeocodingCoordinatesEntity[].class);
        } catch (HttpStatusCodeException e) {
            // Capturamos y relanzamos excepciones relacionadas con errores HTTP
            throw new Exception("Error while calling Geocoding API: " + e.getMessage(), e);
        }

        // Validamos que la respuesta contenga datos
        GeocodingCoordinatesEntity[] responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.length == 0) {
            throw new Exception("Geocoding API returned null or empty response.");
        }

        // Devolvemos el primer resultado (el más relevante)
        return Objects.requireNonNull(responseBody[0]);
    }
}