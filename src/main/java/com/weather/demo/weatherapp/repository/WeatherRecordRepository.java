package com.weather.demo.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.weather.demo.weatherapp.model.WeatherRecord;

// Interfaz repositorio que gestiona operaciones de persistencia para entidades WeatherRecord
// Proporciona métodos CRUD predefinidos gracias a la extensión de JpaRepository
public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {
    // Al extender JpaRepository<WeatherRecord, Long>, esta interfaz:
    // - Trabaja con entidades de tipo WeatherRecord
    // - Usa Long como tipo de dato para la clave primaria
    // - Hereda métodos como save(), findById(), findAll(), deleteById(), etc.

    // No necesita implementación ya que Spring Data JPA genera automáticamente
    // el código necesario en tiempo de ejecución
}