package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.domain.CityCoordinates;
import com.weather.demo.weatherapp.domain.CityWeather;
import com.weather.demo.weatherapp.domain.WeatherRequestDetails;
import com.weather.demo.weatherapp.entity.WeatherResponse;
import com.weather.demo.weatherapp.provider.GeocodingProvider;
import com.weather.demo.weatherapp.provider.WeatherProvider;
import com.weather.demo.weatherapp.transformer.GeocodingCoordinatesTransformer;
import com.weather.demo.weatherapp.transformer.OpenWeatherTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Servicio principal que coordina el flujo de obtención y transformación de datos meteorológicos
// Actúa como orquestador entre los proveedores de datos y los transformadores
@Service // Marca esta clase como componente de servicio gestionado por Spring
public class WeatherService {

    // Proveedor de servicios de geocodificación para obtener coordenadas geográficas
    private GeocodingProvider geocodingProvider;

    // Transformador de datos entre formatos de coordenadas del proveedor y el dominio
    private GeocodingCoordinatesTransformer geocodingCoordinatesTransformer;

    // Proveedor de servicios meteorológicos que consulta APIs externas
    private WeatherProvider weatherProvider;

    // Transformador de datos meteorológicos entre formatos de la API y el dominio
    private OpenWeatherTransformer openWeatherTransformer;

    /**
     * Constructor que inyecta todas las dependencias necesarias para el servicio
     */
    @Autowired
    public WeatherService ( final GeocodingProvider geocodingProvider,
                            final GeocodingCoordinatesTransformer geocodingCoordinatesTransformer,
                            final WeatherProvider weatherProvider,
                            final OpenWeatherTransformer openWeatherTransformer) {
        this.geocodingProvider = geocodingProvider;
        this.geocodingCoordinatesTransformer = geocodingCoordinatesTransformer;
        this.weatherProvider = weatherProvider;
        this.openWeatherTransformer = openWeatherTransformer;
    }

    /**
     * Obtiene datos meteorológicos para los parámetros de solicitud especificados
     *
     * @param weatherRequestDetails Detalles de la solicitud meteorológica (ciudad, etc.)
     * @return Respuesta con la información meteorológica procesada
     * @throws Exception si ocurre algún error durante la obtención o transformación de datos
     */
    public WeatherResponse getWeather(final WeatherRequestDetails weatherRequestDetails) throws Exception {
        // Obtener y transformar coordenadas geográficas para la ciudad solicitada
        final CityCoordinates cityCoordinates = geocodingCoordinatesTransformer
                .transformToDomain(geocodingProvider.getCoordinates(weatherRequestDetails));

        // Obtener y transformar datos meteorológicos usando las coordenadas
        final CityWeather cityWeather = openWeatherTransformer
                .transformToDomain(weatherProvider.getWeather(cityCoordinates));

        // Transformar los datos meteorológicos al formato de respuesta final
        return openWeatherTransformer.transformToEntity(cityWeather);
    }
}