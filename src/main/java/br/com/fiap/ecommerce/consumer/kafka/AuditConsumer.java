package br.com.fiap.ecommerce.consumer.kafka;

import br.com.fiap.ecommerce.model.AuditEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class AuditConsumer {

    private static final String AUDIT_LOG_FILE = "audit-logs.txt";
    private static final DateTimeFormatter formatter = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(
            topics = "${app.kafka.topic.audit}",
            groupId = "audit-consumer-group"
    )
    public void consumeAuditEvent(
            @Payload AuditEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        
        log.info("[AUDIT CONSUMER] Recebido evento de auditoria: {} (Particao: {}, Offset: {})",
                event.getEventType(), partition, offset);
        
        try {
            persistAuditLog(event);
            log.info("Evento de auditoria persistido: {}", event.getEventId());
            
            analyzeSecurityEvent(event);
            
        } catch (IOException e) {
            log.error("Erro ao persistir log de auditoria", e);
        } catch (Exception e) {
            log.error("Erro ao processar evento de auditoria", e);
        }
    }

    private void persistAuditLog(AuditEvent event) throws IOException {
        String logEntry = String.format("[%s] [%s] User: %s | Action: %s | Details: %s%n",
                event.getTimestamp().format(formatter),
                event.getEventType(),
                event.getUserId(),
                event.getAction(),
                event.getDetails());

        if (!Files.exists(Paths.get(AUDIT_LOG_FILE))) {
            Files.createFile(Paths.get(AUDIT_LOG_FILE));
        }

        try (FileWriter writer = new FileWriter(AUDIT_LOG_FILE, true)) {
            writer.write(logEntry);
        }

        log.info("Log de auditoria gravado em arquivo");
    }

    private void analyzeSecurityEvent(AuditEvent event) {
        if (event.getEventType().contains("SECURITY") || 
            event.getAction().contains("FAILED_LOGIN")) {
            
            log.warn("ALERTA DE SEGURANCA: {} - {}", 
                    event.getAction(), event.getDetails());
        }
    }
}
