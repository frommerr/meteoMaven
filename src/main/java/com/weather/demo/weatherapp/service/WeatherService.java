package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.domain.CityCoordinates;
import com.weather.demo.weatherapp.domain.CityWeather;
import com.weather.demo.weatherapp.domain.WeatherRequestDetails;
import com.weather.demo.weatherapp.entity.WeatherResponse;
import com.weather.demo.weatherapp.provider.GeocodingProvider;
import com.weather.demo.weatherapp.provider.WeatherProvider;
import com.weather.demo.weatherapp.transformer.GeocodingCoordinatesTransformer;
import com.weather.demo.weatherapp.transformer.OpenWeatherTransformer;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de orquestar la obtención de información meteorológica para una ciudad.
 * Utiliza proveedores y transformadores para obtener coordenadas geográficas y datos del clima,
 * devolviendo una respuesta estructurada.
 */
@Service
public class WeatherService {

    /**
     * Proveedor para obtener coordenadas geográficas a partir de detalles de la solicitud.
     */
    private GeocodingProvider geocodingProvider;

    /**
     * Transformador para convertir datos de coordenadas a dominio.
     */
    private GeocodingCoordinatesTransformer geocodingCoordinatesTransformer;

    /**
     * Proveedor para obtener datos meteorológicos a partir de coordenadas.
     */
    private WeatherProvider weatherProvider;

    /**
     * Transformador para convertir datos meteorológicos a dominio y entidad.
     */
    private OpenWeatherTransformer openWeatherTransformer;

    /**
     * Constructor que inyecta las dependencias necesarias para el servicio.
     *
     * @param geocodingProvider Proveedor de coordenadas geográficas.
     * @param geocodingCoordinatesTransformer Transformador de coordenadas.
     * @param weatherProvider Proveedor de datos meteorológicos.
     * @param openWeatherTransformer Transformador de datos meteorológicos.
     */
    public WeatherService(final GeocodingProvider geocodingProvider,
                          final GeocodingCoordinatesTransformer geocodingCoordinatesTransformer,
                          final WeatherProvider weatherProvider,
                          final OpenWeatherTransformer openWeatherTransformer) {
        this.geocodingProvider = geocodingProvider;
        this.geocodingCoordinatesTransformer = geocodingCoordinatesTransformer;
        this.weatherProvider = weatherProvider;
        this.openWeatherTransformer = openWeatherTransformer;
    }

    /**
     * Obtiene la información meteorológica para una ciudad a partir de los detalles de la solicitud.
     *
     * @param weatherRequestDetails Detalles de la solicitud meteorológica (por ejemplo, nombre de la ciudad).
     * @return Respuesta estructurada con los datos meteorológicos.
     * @throws Exception si ocurre un error durante la obtención o transformación de los datos.
     */
    public WeatherResponse getWeather(final WeatherRequestDetails weatherRequestDetails) throws Exception {
        final CityCoordinates cityCoordinates = geocodingCoordinatesTransformer
                .transformToDomain(geocodingProvider.getCoordinates(weatherRequestDetails));

        final CityWeather cityWeather = openWeatherTransformer
                .transformToDomain(weatherProvider.getWeather(cityCoordinates));

        return openWeatherTransformer.transformToEntity(cityWeather);
    }
}