package com.weather.demo.weatherapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.weather.demo.weatherapp.model.ExampleEntity;
import com.weather.demo.weatherapp.repository.ExampleEntityRepository;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExampleEntityTest {

    @Autowired
    private ExampleEntityRepository repository;

    @Test
    void testConsultaSimple() {
        // Guardar un registro de prueba
        ExampleEntity e = new ExampleEntity();
        e.setNombre("Prueba");
        repository.save(e);

        // Consultar registros
        long count = repository.count();
        assertTrue(count > 0, "La tabla debe tener registros");
    }
}