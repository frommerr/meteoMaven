package com.weather.demo.weatherapp.transformer;

import com.weather.demo.weatherapp.domain.CityCoordinates;
import com.weather.demo.weatherapp.entity.GeocodingCoordinatesEntity;
import org.springframework.stereotype.Service;

// Transformador que convierte entidades de coordenadas geográficas a objetos de dominio
// Proporciona una capa de abstracción entre la representación externa y el modelo interno
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class GeocodingCoordinatesTransformer {

    /**
     * Transforma una entidad de coordenadas geográficas a un objeto del dominio
     *
     * @param entity Entidad con coordenadas recibidas del proveedor de geocodificación
     * @return Objeto de dominio CityCoordinates con las coordenadas procesadas
     */
    public CityCoordinates transformToDomain(final GeocodingCoordinatesEntity entity) {
        return CityCoordinates.builder()
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}