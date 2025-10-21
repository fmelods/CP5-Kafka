package br.com.fiap.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent implements Serializable {
    private String paymentId;
    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime timestamp;
}
