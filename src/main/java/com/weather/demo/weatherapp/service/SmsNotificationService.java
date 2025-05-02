package com.weather.demo.weatherapp.service;

import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationService.class);

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(toPhoneNumber), // Número del destinatario
                    new com.twilio.type.PhoneNumber(fromPhoneNumber), // Número de Twilio
                    messageBody // Mensaje
            ).create();

            logger.info("Mensaje enviado exitosamente: SID={}", message.getSid());
        } catch (Exception e) {
            logger.error("Error al enviar SMS: {}", e.getMessage(), e);
        }
    }
}