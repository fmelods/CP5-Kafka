package br.com.fiap.ecommerce.controller;

import br.com.fiap.ecommerce.consumer.kafka.OrderConsumer;
import br.com.fiap.ecommerce.model.Order;
import br.com.fiap.ecommerce.producer.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    // ✅ Envia pedido (POST)
    @PostMapping
    public String createOrder(@RequestBody Order order) {
        orderProducer.sendOrder(order);
        return "Pedido recebido e enviado para o Kafka com sucesso!";
    }

    // ✅ Lista pedidos recebidos (GET)
    @GetMapping
    public List<Order> listOrders() {
        return OrderConsumer.getReceivedOrders();
    }
}
