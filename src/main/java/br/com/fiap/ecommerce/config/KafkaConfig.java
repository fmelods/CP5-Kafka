package br.com.fiap.ecommerce.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topic.audit}")
    private String auditTopic;

    @Value("${app.kafka.topic.analytics}")
    private String analyticsTopic;

    @Bean
    public NewTopic auditTopic() {
        return TopicBuilder.name(auditTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic analyticsTopic() {
        return TopicBuilder.name(analyticsTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
