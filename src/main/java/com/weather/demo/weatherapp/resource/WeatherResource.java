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

// Controlador REST que expone endpoints para consultar información meteorológica
// Actúa como punto de entrada para las solicitudes HTTP relacionadas con el clima
@RestController // Combina @Controller y @ResponseBody, indicando que devuelve directamente datos como JSON/XML
@RequestMapping("/api/v1/weather") // Define la ruta base para todos los endpoints de este controlador
public class WeatherResource {

    // Configuración del logger para registrar eventos y errores durante la ejecución
    private static final Logger logger = LoggerFactory.getLogger(WeatherResource.class);

    // Inyección del servicio que se comunica con APIs externas de clima
    @Autowired
    private WeatherApiService weatherApiService;

    // Inyección del servicio que gestiona la persistencia de datos meteorológicos
    @Autowired
    private WeatherRecordService weatherRecordService;

    /**
     * Endpoint que recupera información meteorológica para una ciudad específica
     * Obtiene datos de una API externa y los almacena en la base de datos
     *
     * @param city Nombre de la ciudad para la cual se solicita información meteorológica
     * @return Respuesta HTTP con los datos meteorológicos guardados o mensaje de error
     */
    @GetMapping("/{city}") // Mapea solicitudes GET a la ruta /api/v1/weather/{city}
    public ResponseEntity<?> getWeather(@PathVariable("city") String city) {
        logger.info("Solicitud recibida para ciudad: {}", city);

        try {
            // Obtener datos reales desde la API para la ciudad especificada
            Map<String, Object> weatherData = weatherApiService.getCurrentWeather(city);

            // Guardar los datos en la base de datos
            WeatherRecord savedRecord = weatherRecordService.saveWeatherData(weatherData);

            return ResponseEntity.ok(savedRecord);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Ocurrió un error al procesar la solicitud.");
        }
    }
}