package com.weather.demo.weatherapp.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void initTwilio() {
        // Inicialización de Twilio con las credenciales
        Twilio.init(accountSid, authToken);
        logger.info("Twilio configurado correctamente: Account SID = {}", accountSid);
        System.out.println("Twilio configurado correctamente: Account SID = " + accountSid);
    }
}