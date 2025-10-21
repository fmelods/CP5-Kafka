package br.com.fiap.ecommerce.producer;

import br.com.fiap.ecommerce.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${app.kafka.topic.order}")
    private String orderTopic;

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        try {
            kafkaTemplate.send(orderTopic, order);
            log.info("[ORDER PRODUCER] Evento de pedido enviado com sucesso: {}", order.getId());
        } catch (Exception e) {
            log.error("[ORDER PRODUCER] Falha ao enviar evento de pedido {}: {}", order.getId(), e.getMessage());
        }
    }
}
