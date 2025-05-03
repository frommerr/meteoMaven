package com.weather.demo.weatherapp.service;

import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Servicio que proporciona funcionalidad para enviar notificaciones SMS
// utilizando la plataforma Twilio como proveedor de mensajería
@Service // Marca esta clase como un componente de servicio gestionado por Spring
public class SmsNotificationService {

    // Configuración del logger para registrar eventos y errores durante la ejecución
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
            logger.info("Mensaje enviado exitosamente: SID={}", message.getSid());
        } catch (Exception e) {
            // Registrar cualquier error durante el envío
            logger.error("Error al enviar SMS: {}", e.getMessage(), e);
        }
    }
}