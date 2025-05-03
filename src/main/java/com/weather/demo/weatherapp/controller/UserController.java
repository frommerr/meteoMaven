package com.weather.demo.weatherapp.controller;

import com.weather.demo.weatherapp.model.User;
import com.weather.demo.weatherapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Lista de códigos de país válidos (puedes expandirla según sea necesario)
    private static final List<String> VALID_COUNTRY_CODES = Arrays.asList("+34", "+1", "+44", "+49", "+33");

    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            // Verificar si el número de teléfono ya existe
            if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El número de teléfono ya está registrado.");
            }

            // TODO: Corregir la logica de comprobacion de codigo del pais
            /*
            // Verificar si el código de país es válido
            String countryCode = user.getPhoneNumber().substring(0, user.getPhoneNumber().indexOf("9") - 1); // Extraer código
            if (!VALID_COUNTRY_CODES.contains(countryCode)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El código de país no es válido: " + countryCode);
            }
            */

            // Guardar el usuario en la base de datos
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
}