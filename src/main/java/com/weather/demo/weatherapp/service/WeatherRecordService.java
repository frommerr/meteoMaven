package com.weather.demo.weatherapp.service;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.repository.WeatherRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Servicio encargado de procesar y almacenar registros meteorológicos en la base de datos.
 * Recibe datos en formato de mapa, los transforma en entidades {@link WeatherRecord} y los persiste.
 */
@Service
public class WeatherRecordService {

    /**
     * Logger para registrar información y errores durante el procesamiento y almacenamiento de datos meteorológicos.
     */
    private static final Logger logger = LoggerFactory.getLogger(WeatherRecordService.class);

    /**
     * Repositorio para acceder y almacenar entidades {@link WeatherRecord}.
     */
    @Autowired
    private WeatherRecordRepository repository;

    /**
     * Procesa y guarda los datos meteorológicos recibidos en la base de datos.
     *
     * @param weatherData Mapa con los datos meteorológicos obtenidos de la API externa.
     * @return El registro meteorológico guardado en la base de datos.
     */
    public WeatherRecord saveWeatherData(Map<String, Object> weatherData) {
        logger.info("Guardando datos del clima: {}", weatherData);

        try {
            WeatherRecord record = new WeatherRecord();

            var weatherArray = (java.util.List<Map<String, Object>>) weatherData.get("weather");
            if (weatherArray != null && !weatherArray.isEmpty()) {
                Map<String, Object> weatherDetails = weatherArray.get(0);
                record.setWeather((String) weatherDetails.getOrDefault("main", "Unknown"));
                record.setDetails((String) weatherDetails.getOrDefault("description", "No description"));
            } else {
                record.setWeather("Unknown");
                record.setDetails("No description");
            }

            Object weatherId = weatherData.get("id");
            record.setWeatherId(weatherId != null ? weatherId.toString() : "N/A");

            var mainData = (Map<String, Object>) weatherData.get("main");
            if (mainData != null) {
                record.setTemperature(parseDouble(mainData.get("temp"), 0.0));
                record.setFeelsLike(parseDouble(mainData.get("feels_like"), 0.0));
                record.setHumidity(parseInteger(mainData.get("humidity"), 0));
                record.setPressure(parseInteger(mainData.get("pressure"), 0));
            } else {
                logger.warn("No se encontró la clave 'main' en los datos del clima.");
            }

            var windData = (Map<String, Object>) weatherData.get("wind");
            if (windData != null) {
                record.setWindSpeed(parseDouble(windData.get("speed"), 0.0));
                record.setWindDeg(parseInteger(windData.get("deg"), 0));
                record.setWindGust(parseDouble(windData.get("gust"), 0.0));
            } else {
                logger.warn("No se encontró la clave 'wind' en los datos del clima.");
            }

            var rainData = (Map<String, Object>) weatherData.get("rain");
            if (rainData != null) {
                record.setRain1h(parseDouble(rainData.get("1h"), 0.0));
            } else {
                logger.warn("No se encontró la clave 'rain' en los datos del clima.");
            }

            record.setConsultTimestamp(LocalDateTime.now());

            WeatherRecord savedRecord = repository.save(record);
            logger.info("Registro guardado exitosamente: {}", savedRecord);
            return savedRecord;
        } catch (Exception e) {
            logger.error("Error al guardar datos del clima: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Convierte un valor a Double, usando un valor predeterminado si la conversión falla.
     *
     * @param value Valor a convertir.
     * @param defaultValue Valor predeterminado en caso de error.
     * @return Valor convertido a Double o el valor predeterminado.
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
     * Convierte un valor a Integer, usando un valor predeterminado si la conversión falla.
     *
     * @param value Valor a convertir.
     * @param defaultValue Valor predeterminado en caso de error.
     * @return Valor convertido a Integer o el valor predeterminado.
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