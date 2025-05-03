package com.weather.demo.weatherapp.repository;

import com.weather.demo.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfaz repositorio que gestiona operaciones de persistencia para entidades User
// Proporciona métodos CRUD predefinidos gracias a la extensión de JpaRepository
public interface UserRepository extends JpaRepository<User, Long> {
    // Al extender JpaRepository<User, Long>, esta interfaz:
    // - Trabaja con entidades de tipo User
    // - Usa Long como tipo de dato para la clave primaria
    // - Hereda métodos como save(), findById(), findAll(), deleteById(), etc.

    /**
     * Verifica si existe algún usuario con el número de teléfono especificado
     *
     * @param phoneNumber Número de teléfono a verificar en la base de datos
     * @return true si el número de teléfono ya está registrado, false en caso contrario
     */
    boolean existsByPhoneNumber(String phoneNumber);
}