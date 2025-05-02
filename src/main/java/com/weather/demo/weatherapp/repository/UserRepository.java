package com.weather.demo.weatherapp.repository;

import com.weather.demo.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Verificar si un número de teléfono ya existe
    boolean existsByPhoneNumber(String phoneNumber);
}