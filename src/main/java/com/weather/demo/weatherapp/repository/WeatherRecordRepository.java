package com.weather.demo.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.weather.demo.weatherapp.model.WeatherRecord;

/**
 * Repositorio de acceso a datos para la entidad {@link WeatherRecord}.
 * Proporciona operaciones CRUD y consultas personalizadas mediante Spring Data JPA.
 * <p>
 * Al extender {@code JpaRepository}, hereda métodos como:
 * <ul>
 *   <li>save()</li>
 *   <li>findById()</li>
 *   <li>findAll()</li>
 *   <li>deleteById()</li>
 * </ul>
 * No requiere implementación manual, ya que Spring genera el código automáticamente.
 */
public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {
    // Métodos CRUD y de consulta heredados de JpaRepository
}