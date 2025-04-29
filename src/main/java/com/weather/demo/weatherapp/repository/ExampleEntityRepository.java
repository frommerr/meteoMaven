package com.weather.demo.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.weather.demo.weatherapp.model.ExampleEntity;

public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, Long> {

}