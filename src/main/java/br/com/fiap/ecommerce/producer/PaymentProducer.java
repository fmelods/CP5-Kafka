package br.com.fiap.ecommerce.producer;

import br.com.fiap.ecommerce.model.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    private static final Logger log = LoggerFactory.getLogger(PaymentProducer.class);

    @Value("${app.kafka.topic.payment}")
    private String paymentTopic;

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(PaymentEvent event) {
        try {
            kafkaTemplate.send(paymentTopic, event);
            log.info("[PAYMENT PRODUCER] Evento de pagamento {} enviado com sucesso!", event.getPaymentId());
        } catch (Exception e) {
            log.error("[PAYMENT PRODUCER] Falha ao enviar pagamento {}: {}", event.getPaymentId(), e.getMessage());
        }
    }
}
