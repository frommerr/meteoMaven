package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa a un usuario en la aplicación de clima.
 * Incluye información personal y de contacto, con validaciones para asegurar la integridad de los datos.
 */
@Getter // Genera automáticamente métodos getter para todos los campos
@Setter // Genera automáticamente métodos setter para todos los campos
@Entity // Define esta clase como entidad persistible en la base de datos
@Table(name = "app_user") // Cambia el nombre de la tabla para evitar conflictos con palabras reservadas en SQL
public class User {

    /**
     * Identificador único del usuario en la base de datos.
     */
    @Id // Marca este campo como la clave primaria de la entidad
    private Long id;

    /**
     * Nombre completo del usuario.
     * Debe tener entre 2 y 50 caracteres y solo puede contener letras y espacios.
     */
    @NotNull(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo puede contener letras y espacios.")
    private String name;

    /**
     * Número de teléfono del usuario en formato internacional, incluyendo el código de país.
     * Debe tener un código de país válido y 9 dígitos, sin caracteres adicionales.
     */
    @NotNull(message = "El número de teléfono no puede estar vacío.")
    @Pattern(
            regexp = "^\\+\\d{1,4}[1-9]\\d{8}$",
            message = "El número de teléfono debe incluir un código de país válido y tener 9 dígitos sin caracteres adicionales."
    )
    private String phoneNumber;
}