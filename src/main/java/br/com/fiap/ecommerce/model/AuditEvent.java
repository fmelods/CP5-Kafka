package br.com.fiap.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent implements Serializable {
    private String eventId;
    private String eventType;
    private String userId;
    private String action;
    private String details;
    private LocalDateTime timestamp;
}
