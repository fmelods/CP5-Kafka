package br.com.fiap.ecommerce.consumer.kafka;

import br.com.fiap.ecommerce.model.PaymentEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic.payment}", groupId = "ecommerce-group")
    public void consumePaymentEvent(@Payload PaymentEvent event, ConsumerRecord<String, PaymentEvent> record) {
        log.info("[PAYMENT CONSUMER] Evento de pagamento recebido: {} (partição {}, offset {})",
                event.getPaymentId(), record.partition(), record.offset());

        try {
            processPayment(event);
            log.info("[PAYMENT CONSUMER] Pagamento processado com sucesso: {}", event.getPaymentId());
        } catch (Exception e) {
            log.error("[PAYMENT CONSUMER] Falha ao processar pagamento {}: {}", event.getPaymentId(), e.getMessage());
        }
    }

    private void processPayment(PaymentEvent event) {
        // Simula processamento do pagamento (ex: atualização de status, persistência, etc.)
        log.info("[PAYMENT CONSUMER] Processando pagamento de R${} para o pedido {}...",
                event.getAmount(), event.getOrderId());
    }
}
