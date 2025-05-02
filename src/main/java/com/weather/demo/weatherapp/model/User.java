package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user") // Cambiamos el nombre de la tabla para evitar conflictos con palabras reservadas
public class User {

    @Id
    private Long id;

    @NotNull(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo puede contener letras y espacios.")
    private String name;

    @NotNull(message = "El número de teléfono no puede estar vacío.")
    @Pattern(
            regexp = "^\\+\\d{1,4}[1-9]\\d{8}$",
            message = "El número de teléfono debe incluir un código de país válido y tener 9 dígitos sin caracteres adicionales."
    )
    private String phoneNumber;
    
}