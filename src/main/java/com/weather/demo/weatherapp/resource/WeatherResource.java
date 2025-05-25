package com.weather.demo.weatherapp.resource;

import com.weather.demo.weatherapp.model.WeatherRecord;
import com.weather.demo.weatherapp.service.WeatherApiService;
import com.weather.demo.weatherapp.service.WeatherRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Recurso REST que expone endpoints para consultar información meteorológica de una ciudad.
 * Gestiona las solicitudes HTTP relacionadas con el clima y delega la lógica a los servicios correspondientes.
 */
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherResource {

    /**
     * Logger para registrar información y errores durante la ejecución de las peticiones.
     */
    private static final Logger logger = LoggerFactory.getLogger(WeatherResource.class);

    /**
     * Servicio encargado de obtener los datos meteorológicos actuales desde una API externa.
     */
    @Autowired
    private WeatherApiService weatherApiService;

    /**
     * Servicio encargado de almacenar los registros meteorológicos en la base de datos.
     */
    @Autowired
    private WeatherRecordService weatherRecordService;

    /**
     * Endpoint para obtener la información meteorológica actual de una ciudad.
     *
     * @param city Nombre de la ciudad para la cual se solicita la información meteorológica.
     * @return {@link ResponseEntity} con el registro meteorológico guardado o un mensaje de error en caso de fallo.
     */
    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable("city") String city) {
        logger.info("Solicitud recibida para ciudad: {}", city);

        try {
            Map<String, Object> weatherData = weatherApiService.getCurrentWeather(city);
            WeatherRecord savedRecord = weatherRecordService.saveWeatherData(weatherData);
            return ResponseEntity.ok(savedRecord);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Ocurrió un error al procesar la solicitud.");
        }
    }
}