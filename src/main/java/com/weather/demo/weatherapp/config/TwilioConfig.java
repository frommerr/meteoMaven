package com.weather.demo.weatherapp.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

// Clase de configuración para inicializar y configurar el cliente Twilio para envío de SMS
@Configuration
public class TwilioConfig {
    // Logger para registrar eventos relacionados con la configuración de Twilio
    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    // ID de cuenta de Twilio obtenido desde application.properties
    @Value("${twilio.account.sid}")
    private String accountSid;

    // Token de autenticación de Twilio obtenido desde application.properties
    @Value("${twilio.auth.token}")
    private String authToken;

    // Inicializa el cliente Twilio después de que se crea el bean
    @PostConstruct
    public void initTwilio() {
        // Inicialización de Twilio con las credenciales
        Twilio.init(accountSid, authToken);
        logger.info("Twilio configurado correctamente: Account SID = {}", accountSid);
        System.out.println("Twilio configurado correctamente: Account SID = " + accountSid);
    }
}