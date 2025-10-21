package br.com.fiap.ecommerce.producer;

import br.com.fiap.ecommerce.model.AuditEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditProducer {

    private final KafkaTemplate<String, AuditEvent> kafkaTemplate;

    @Value("${app.kafka.topic.audit}")
    private String auditTopic;

    public void publishAuditEvent(String eventType, String userId, String action, String details) {
        AuditEvent event = AuditEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .userId(userId)
                .action(action)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        log.info("Publicando evento de auditoria: {}", event.getEventType());
        kafkaTemplate.send(auditTopic, event.getEventId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Evento de auditoria publicado: offset {}", 
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Erro ao publicar evento de auditoria", ex);
                    }
                });
    }
}
