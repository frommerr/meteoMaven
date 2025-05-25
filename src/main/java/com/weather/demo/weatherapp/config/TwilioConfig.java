package com.weather.demo.weatherapp.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

/**
 * Clase de configuración para inicializar y configurar el cliente de Twilio
 * utilizado para el envío de SMS en la aplicación.
 */
@Configuration
public class TwilioConfig {
    /**
     * Logger para registrar eventos relacionados con la configuración de Twilio.
     */
    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    /**
     * SID de la cuenta de Twilio, obtenido desde el archivo de propiedades.
     */
    @Value("${twilio.account.sid}")
    private String accountSid;

    /**
     * Token de autenticación de Twilio, obtenido desde el archivo de propiedades.
     */
    @Value("${twilio.auth.token}")
    private String authToken;

    /**
     * Inicializa el cliente de Twilio con las credenciales configuradas.
     * Este método se ejecuta automáticamente después de la creación del bean.
     */
    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
        logger.info("Twilio configurado correctamente: Account SID = {}", accountSid);
        System.out.println("Twilio configurado correctamente: Account SID = " + accountSid);
    }
}