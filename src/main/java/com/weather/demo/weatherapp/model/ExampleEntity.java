package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// Clase entidad de ejemplo que demuestra la estructura básica de una entidad JPA en la aplicación
@Entity // Define esta clase como entidad persistible en la base de datos
@Getter // Genera automáticamente métodos getter para todos los campos
@Setter // Genera automáticamente métodos setter para todos los campos
public class ExampleEntity {
    // Identificador único autogenerado para cada instancia de esta entidad
    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue // Configura la generación automática de valores para la clave primaria
    private Long id;

    // Campo que almacena el nombre asociado a esta entidad
    private String nombre;
}