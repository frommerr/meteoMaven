package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.repository.WeatherRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

// Servicio que gestiona la persistencia de datos meteorológicos
// Se encarga de transformar los datos recibidos de la API externa en entidades WeatherRecord
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class WeatherRecordService {

    // Configuración del logger para registrar eventos y errores durante la ejecución
    private static final Logger logger = LoggerFactory.getLogger(WeatherRecordService.class);

    // Repositorio para realizar operaciones de persistencia con entidades WeatherRecord
    @Autowired
    private WeatherRecordRepository repository;

    /**
     * Transforma y guarda los datos meteorológicos recibidos de la API externa
     *
     * @param weatherData Mapa con los datos del clima obtenidos de la API externa
     * @return Entidad WeatherRecord persistida en la base de datos
     * @throws Exception si ocurre algún error durante el proceso
     */
    public WeatherRecord saveWeatherData(Map<String, Object> weatherData) {
        logger.info("Guardando datos del clima: {}", weatherData);

        try {
            WeatherRecord record = new WeatherRecord();

            // Procesar datos del clima (condición, descripción)
            var weatherArray = (java.util.List<Map<String, Object>>) weatherData.get("weather");
            if (weatherArray != null && !weatherArray.isEmpty()) {
                Map<String, Object> weatherDetails = weatherArray.get(0);
                record.setWeather((String) weatherDetails.getOrDefault("main", "Unknown"));
                record.setDetails((String) weatherDetails.getOrDefault("description", "No description"));
            } else {
                record.setWeather("Unknown");
                record.setDetails("No description");
            }

            // Guardar identificador de la condición meteorológica
            Object weatherId = weatherData.get("id");
            record.setWeatherId(weatherId != null ? weatherId.toString() : "N/A");

            // Procesar datos numéricos (temperatura, sensación térmica, humedad, presión)
            var mainData = (Map<String, Object>) weatherData.get("main");
            if (mainData != null) {
                record.setTemperature(parseDouble(mainData.get("temp"), 0.0));
                record.setFeelsLike(parseDouble(mainData.get("feels_like"), 0.0));
                record.setHumidity(parseInteger(mainData.get("humidity"), 0));
                record.setPressure(parseInteger(mainData.get("pressure"), 0));
            } else {
                logger.warn("No se encontró la clave 'main' en los datos del clima.");
            }

            // Registrar fecha y hora de la consulta
            record.setConsultTimestamp(LocalDateTime.now());

            // Persistir y devolver la entidad guardada
            WeatherRecord savedRecord = repository.save(record);
            logger.info("Registro guardado exitosamente: {}", savedRecord);
            return savedRecord;
        } catch (Exception e) {
            logger.error("Error al guardar datos del clima: {}", e.getMessage(), e);
            throw e; // Propagar la excepción para manejo en capa superior
        }
    }

    /**
     * Convierte un objeto a Double de forma segura
     *
     * @param value Valor a convertir
     * @param defaultValue Valor por defecto si la conversión falla
     * @return El valor convertido o el valor por defecto
     */
    private Double parseDouble(Object value, Double defaultValue) {
        try {
            return value != null ? Double.valueOf(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("No se pudo convertir el valor {} a Double. Usando valor predeterminado {}", value, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Convierte un objeto a Integer de forma segura
     *
     * @param value Valor a convertir
     * @param defaultValue Valor por defecto si la conversión falla
     * @return El valor convertido o el valor por defecto
     */
    private Integer parseInteger(Object value, Integer defaultValue) {
        try {
            return value != null ? Integer.valueOf(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("No se pudo convertir el valor {} a Integer. Usando valor predeterminado {}", value, defaultValue);
            return defaultValue;
        }
    }
}