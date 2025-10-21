package br.com.fiap.ecommerce.consumer.kafka;

import br.com.fiap.ecommerce.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    // Lista estática para armazenar os pedidos recebidos (em memória)
    private static final List<Order> receivedOrders = Collections.synchronizedList(new ArrayList<>());

    @KafkaListener(
            topics = "${app.kafka.topic.order}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeOrder(ConsumerRecord<String, Order> record) {
        Order order = record.value();

        if (order != null) {
            receivedOrders.add(order); // Armazena o pedido na lista
            log.info("[ORDER CONSUMER] Pedido recebido: {}", order.getId());
            log.info("Cliente: {}", order.getCustomerName());
            log.info("Total: R$ {}", order.getTotal());
            log.info("Quantidade de itens: {}", order.getItems() != null ? order.getItems().size() : 0);
        } else {
            log.warn("[ORDER CONSUMER] Pedido recebido é nulo!");
        }
    }

    // Método auxiliar para acessar os pedidos
    public static List<Order> getReceivedOrders() {
        return new ArrayList<>(receivedOrders);
    }
}
