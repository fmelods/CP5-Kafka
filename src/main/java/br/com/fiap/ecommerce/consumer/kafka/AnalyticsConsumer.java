package br.com.fiap.ecommerce.consumer.kafka;

import br.com.fiap.ecommerce.model.AnalyticsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AnalyticsConsumer {

    private final Map<String, MetricsSummary> metricsCache = new ConcurrentHashMap<>();

    @KafkaListener(
            topics = "${app.kafka.topic.analytics}",
            groupId = "analytics-consumer-group"
    )
    public void consumeAnalyticsEvent(
            @Payload AnalyticsEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        
        log.info("[ANALYTICS CONSUMER] Recebido evento: {} (Particao: {}, Offset: {})",
                event.getEventType(), partition, offset);
        
        try {
            processMetrics(event);
            generateReport(event.getCategory());
            
            log.info("Evento de analytics processado: {}", event.getEventId());
            
        } catch (Exception e) {
            log.error("Erro ao processar evento de analytics", e);
            throw e;
        }
    }

    private void processMetrics(AnalyticsEvent event) {
        String category = event.getCategory();
        
        metricsCache.compute(category, (key, summary) -> {
            if (summary == null) {
                summary = new MetricsSummary();
            }
            summary.incrementCount();
            summary.addValue(event.getValue());
            return summary;
        });

        log.info("Metricas atualizadas para categoria: {}", category);
    }

    private void generateReport(String category) {
        MetricsSummary summary = metricsCache.get(category);
        
        if (summary != null) {
            log.info("""
                    
                    =======================================
                    RELATORIO DE ANALYTICS - {}
                    =======================================
                    Total de Eventos: {}
                    Valor Total: R$ {}
                    Valor Medio: R$ {}
                    =======================================
                    """,
                    category,
                    summary.getCount(),
                    summary.getTotalValue(),
                    summary.getAverageValue());
        }
    }

    private static class MetricsSummary {
        private long count = 0;
        private BigDecimal totalValue = BigDecimal.ZERO;

        public void incrementCount() {
            count++;
        }

        public void addValue(BigDecimal value) {
            if (value != null) {
                totalValue = totalValue.add(value);
            }
        }

        public long getCount() {
            return count;
        }

        public BigDecimal getTotalValue() {
            return totalValue;
        }

        public BigDecimal getAverageValue() {
            if (count == 0) return BigDecimal.ZERO;
            return totalValue.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
