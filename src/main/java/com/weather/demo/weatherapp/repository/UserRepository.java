package com.weather.demo.weatherapp.repository;

import com.weather.demo.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de acceso a datos para la entidad {@link User}.
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
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Verifica si existe algún usuario con el número de teléfono especificado.
     *
     * @param phoneNumber Número de teléfono a verificar en la base de datos.
     * @return {@code true} si el número de teléfono ya está registrado, {@code false} en caso contrario.
     */
    boolean existsByPhoneNumber(String phoneNumber);
}