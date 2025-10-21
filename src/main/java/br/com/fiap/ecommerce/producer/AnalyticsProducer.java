package br.com.fiap.ecommerce.producer;

import br.com.fiap.ecommerce.model.AnalyticsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsProducer {

    private final KafkaTemplate<String, AnalyticsEvent> kafkaTemplate;

    @Value("${app.kafka.topic.analytics}")
    private String analyticsTopic;

    public void publishAnalyticsEvent(String eventType, String category, 
                                     BigDecimal value, String metadata) {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .category(category)
                .value(value)
                .metadata(metadata)
                .timestamp(LocalDateTime.now())
                .build();

        log.info("Publicando evento de analytics: {}", event.getEventType());
        kafkaTemplate.send(analyticsTopic, event.getCategory(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Evento de analytics publicado na particao {}", 
                                result.getRecordMetadata().partition());
                    } else {
                        log.error("Erro ao publicar evento de analytics", ex);
                    }
                });
    }
}
