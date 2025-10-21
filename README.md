# Sistema de E-commerce com RabbitMQ e Kafka

Projeto Java Spring Boot que demonstra o uso de RabbitMQ e Apache Kafka para processamento assíncrono e streaming de eventos.

## Integrantes do Grupo

- Arthur Ramos Dos Santos - RM558798  
- Cauã Sanches de Santana - RM558317  
- Felipe Melo e Sousa - RM556099  
- Heitor Romero da Rocha - RM558301

## Arquitetura

### RabbitMQ (Tarefas Assíncronas)
- **OrderProducer**: Publica pedidos para processamento
- **PaymentProducer**: Processa notificações de pagamento
- **EmailConsumer**: Envia emails via API externa
- **InventoryConsumer**: Atualiza estoque consultando API de fornecedores

### Kafka (Streaming de Eventos)
- **AuditProducer**: Registra eventos de auditoria
- **AnalyticsProducer**: Envia métricas para analytics
- **AuditConsumer**: Persiste logs em arquivo e analisa segurança
- **AnalyticsConsumer**: Processa métricas em tempo real

## Requisitos

- Java 21
- Docker e Docker Compose
- Gradle

## Como Executar

### 1. Subir a infraestrutura

\`\`\`bash
docker-compose up -d
\`\`\`

### 2. Verificar serviços

\`\`\`bash
docker-compose ps
\`\`\`

### 3. Acessar interfaces

- RabbitMQ Management: http://localhost:15672 (guest/guest)
- Kafka UI: http://localhost:8090

### 4. Executar aplicação

\`\`\`bash
./gradlew bootRun
\`\`\`

## Testando

### Criar um pedido

\`\`\`bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "customer-123",
    "customerEmail": "cliente@example.com",
    "items": [
      {
        "productName": "Notebook Dell",
        "quantity": 1,
        "price": 3500.00
      },
      {
        "productName": "Mouse Logitech",
        "quantity": 2,
        "price": 150.00
      }
    ]
  }'
\`\`\`

## Observando o Fluxo

1. Verifique os logs da aplicação para ver os produtores e consumidores em ação
2. Acesse o RabbitMQ Management para ver as filas e mensagens
3. Acesse o Kafka UI para ver os tópicos e offsets
4. Verifique o arquivo `audit-logs.txt` criado pelo AuditConsumer

## Conceitos Implementados

### RabbitMQ
- Topic Exchange para roteamento flexível
- Múltiplas filas com bindings
- Conversão JSON automática
- ACK automático de mensagens

### Kafka
- Tópicos com múltiplas partições
- Consumer Groups
- Serialização JSON
- Offset management
- Processamento em tempo real

## Estrutura do Projeto

\`\`\`
src/main/java/br/com/fiap/ecommerce/
├── config/              # Configurações RabbitMQ e Kafka
├── model/               # Modelos de dados
├── producer/
│   ├── rabbitmq/       # Produtores RabbitMQ
│   └── kafka/          # Produtores Kafka
├── consumer/
│   ├── rabbitmq/       # Consumidores RabbitMQ
│   └── kafka/          # Consumidores Kafka
└── controller/         # REST Controllers
\`\`\`

## Autor

Projeto desenvolvido para demonstração de conceitos de mensageria e streaming de eventos.
\`\`\`

```text file=".gitignore"
HELP.md
.gradle
build/
!gradle/wrapper/gradle-wrapper.jar
!**/src/main/**/build/
!**/src/test/**/build/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache
bin/
!**/src/main/**/bin/
!**/src/test/**/bin/

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
out/
!**/src/main/**/out/
!**/src/test/**/out/

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/

### VS Code ###
.vscode/

### Kafka & RabbitMQ ###
kafka-data/
kraft-combined-logs/

### Logs ###
*.log
audit-logs.txt
