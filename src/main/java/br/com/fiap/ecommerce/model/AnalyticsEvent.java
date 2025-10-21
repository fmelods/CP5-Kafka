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
public class AnalyticsEvent implements Serializable {
    private String eventId;
    private String eventType;
    private String category;
    private BigDecimal value;
    private String metadata;
    private LocalDateTime timestamp;
}
