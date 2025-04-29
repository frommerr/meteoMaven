package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExampleEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
}