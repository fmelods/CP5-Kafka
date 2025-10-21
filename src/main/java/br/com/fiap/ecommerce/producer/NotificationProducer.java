package br.com.fiap.ecommerce.producer;

import br.com.fiap.ecommerce.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.notification}")
    private String notificationTopic;

    public NotificationProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderNotification(String message) {
        try {
            NotificationEvent event = new NotificationEvent(message);
            kafkaTemplate.send(notificationTopic, event);
            log.info("[NOTIFICATION PRODUCER] Notificação enviada para o tópico '{}': {}", notificationTopic, message);
        } catch (Exception e) {
            log.error("[NOTIFICATION PRODUCER] Erro ao enviar notificação: {}", e.getMessage());
        }
    }
}
