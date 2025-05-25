package com.weather.demo.weatherapp.controller;

import com.weather.demo.weatherapp.model.User;
import com.weather.demo.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controlador REST que gestiona operaciones CRUD para usuarios.
 * Permite crear y listar usuarios, validando el formato del número de teléfono.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    /**
     * Conjunto de códigos de país válidos para números de teléfono (códigos europeos).
     */
    private static final Set<String> VALID_COUNTRY_CODES = Set.of(
            "30", "31", "32", "33", "34", "36", "39", "40", "41", "43", "44", "45", "46", "47", "48", "49",
            "351", "352", "353", "354", "355", "356", "357", "358", "359", "370", "371", "372", "373", "374",
            "375", "376", "377", "378", "379", "380", "381", "382", "383", "385", "386", "387", "389", "420",
            "421", "423"
    );

    /**
     * Repositorio para la persistencia de datos de usuarios.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Crea un nuevo usuario tras validar su número de teléfono.
     *
     * @param user Objeto usuario recibido en el cuerpo de la petición.
     * @return Respuesta HTTP indicando el resultado de la operación.
     */
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            String phoneNumber = user.getPhoneNumber();
            if (!isValidPhoneNumber(phoneNumber)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El número de teléfono no es válido: " + phoneNumber);
            }
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado.");
        }
    }

    /**
     * Devuelve la lista completa de todos los usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Valida que el número de teléfono tenga formato internacional válido con código de país europeo.
     *
     * @param phoneNumber Número de teléfono a validar.
     * @return true si el número es válido, false en caso contrario.
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("+")) {
            return false;
        }
        String countryCode = phoneNumber.substring(1, phoneNumber.length() - 9);
        if (!VALID_COUNTRY_CODES.contains(countryCode)) {
            return false;
        }
        return phoneNumber.matches("^\\+\\d{1,4}\\d{9}$");
    }
}