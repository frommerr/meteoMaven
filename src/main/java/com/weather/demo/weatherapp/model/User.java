package com.weather.demo.weatherapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "app_user") // Cambia el nombre de la tabla
public class User {

    @Id
    private Long id;
    private String name;
    private String phoneNumber;

}