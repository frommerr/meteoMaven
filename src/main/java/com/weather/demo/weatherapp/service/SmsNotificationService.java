package com.weather.demo.weatherapp.service;

import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Servicio que proporciona funcionalidad para enviar notificaciones SMS
@Service
public class SmsNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationService.class);

    // Número de teléfono remitente configurado en las propiedades de la aplicación
    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    /**
     * Envía un mensaje SMS al número de teléfono especificado
     *
     * @param toPhoneNumber Número de teléfono del destinatario
     * @param messageBody Contenido del mensaje a enviar
     */
    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            // Crear y enviar el mensaje usando la API de Twilio
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(toPhoneNumber), // Número del destinatario
                    new com.twilio.type.PhoneNumber(fromPhoneNumber), // Número de Twilio
                    messageBody // Mensaje
            ).create();

            // Registrar éxito en el envío
            logger.info("Mensaje enviado exitosamente a {}: SID={}", toPhoneNumber, message.getSid());
        } catch (Exception e) {
            // Registrar cualquier error durante el envío
            logger.error("Error al enviar SMS a {}: {}", toPhoneNumber, e.getMessage(), e);
        }
    }

    /**
     * Envía un mensaje genérico para cualquier tipo de alerta.
     *
     * @param phoneNumber Número de teléfono del destinatario
     * @param alertMessage Mensaje de alerta a enviar
     */
    public void sendAlertNotification(String phoneNumber, String alertMessage) {
        logger.info("Enviando notificación de alerta: {} a {}", alertMessage, phoneNumber);
        sendSms(phoneNumber, alertMessage);
    }
}