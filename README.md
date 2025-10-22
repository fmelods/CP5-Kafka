# 🛒 Projeto E-commerce — Apache Kafka

## 📘 Descrição do Projeto
Este projeto foi desenvolvido como parte da disciplina de **Java Advanced** da **FIAP**.

O objetivo é demonstrar o uso do **Apache Kafka** em um sistema de e-commerce, utilizando **Java 17** e **Spring Boot 3.4**.  
A aplicação mostra como diferentes serviços podem se comunicar de forma **assíncrona**, **desacoplada** e **escalável**, simulando o fluxo de criação de pedidos, processamento de pagamentos, auditoria e análise de dados.

---

## 🧠 Conceito e Arquitetura

A arquitetura do sistema segue o padrão de **event-driven architecture** (arquitetura orientada a eventos), usando o Kafka para intermediar a comunicação entre produtores e consumidores.

O projeto contém **dois produtores** e **dois consumidores**, cada um responsável por uma parte do fluxo do e-commerce.

### 🔹 Produtores
- **OrderProducer:** publica eventos de criação de pedidos no tópico `orders`.  
- **PaymentProducer:** publica eventos de pagamento no tópico `payments`, simulando a confirmação de pagamento de um pedido.

### 🔹 Consumidores
- **AuditConsumer:** consome mensagens de pedidos e pagamentos, registrando logs de auditoria e segurança.  
- **AnalyticsConsumer:** consome eventos e gera estatísticas em tempo real, simulando um sistema analítico que monitora o desempenho das vendas.

---

## ⚙️ Tecnologias Utilizadas
- **Java 17**  
- **Spring Boot 3.4**  
- **Apache Kafka**  
- **Docker e Docker Compose**  
- **Kafka UI**  
- **Postman** (para testes de API)

---

## 🧩 Estrutura dos Tópicos Kafka

| Tipo de Evento | Nome do Tópico | Responsável |
|----------------|----------------|-------------|
| Pedido Criado  | `orders`       | OrderProducer |
| Pagamento Realizado | `payments` | PaymentProducer |
| Auditoria de Eventos | `audits` | AuditConsumer |
| Métricas e Análises | `analytics` | AnalyticsConsumer |

---

## 🚀 Como Executar o Projeto

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/seu-usuario/java-kafka-project.git
cd java-kafka-project
```

### 2️⃣ Subir os containers
Certifique-se de que o **Docker Desktop** está em execução, então rode:
```bash
docker-compose up -d
```

Isso iniciará:
- Kafka  
- Zookeeper  
- Kafka UI (interface visual)

### 3️⃣ Rodar a aplicação
Execute a aplicação pelo IntelliJ IDEA ou via terminal:
```bash
./gradlew bootRun
```

A aplicação estará disponível em:  
👉 **http://localhost:8080**

---

## 🧾 Endpoints Principais

### Criar um novo pedido
```
POST http://localhost:8080/api/orders
Content-Type: application/json

{
  "orderId": "12345",
  "product": "Notebook Dell",
  "price": 4500.00,
  "quantity": 1,
  "customerEmail": "cliente@exemplo.com"
}
```

### Listar pedidos processados
```
GET http://localhost:8080/api/orders
```

---

## 🧱 Estrutura do Projeto

```
src/main/java/br/com/fiap/ecommerce/
│
├── controller/
│   └── OrderController.java
│
├── producer/
│   ├── OrderProducer.java
│   └── PaymentProducer.java
│
├── consumer/
│   ├── AuditConsumer.java
│   └── AnalyticsConsumer.java
│
├── config/
│   └── KafkaConfig.java
│
└── model/
    └── Order.java
```

---

## 📊 Kafka UI

Após iniciar o Docker, acesse a interface do Kafka UI em:  
🔗 **http://localhost:8090**

Lá é possível visualizar os tópicos `orders`, `payments`, `audits` e `analytics`, bem como inspecionar mensagens em tempo real.

---

## 📈 Fluxo de Funcionamento

1. Um novo pedido é criado via endpoint `/api/orders`.  
2. O **OrderProducer** publica a mensagem no tópico `orders`.  
3. O **AuditConsumer** registra o evento de criação de pedido.  
4. O **PaymentProducer** publica a confirmação no tópico `payments`.  
5. O **AnalyticsConsumer** processa os dados e gera métricas analíticas.  
6. Todo o fluxo é registrado no log da aplicação e pode ser acompanhado pelo **Kafka UI**.

---

## 👥 Integrantes do Grupo

- **Arthur Ramos Dos Santos** — RM558798  
- **Cauã Sanches de Santana** — RM558317  
- **Felipe Melo e Sousa** — RM556099  
- **Heitor Romero da Rocha** — RM558301  

---

## 🏁 Conclusão

O projeto demonstra o uso do **Apache Kafka** para comunicação entre microsserviços de forma **assíncrona e escalável**, simulando um e-commerce real.  
Com dois produtores e dois consumidores, o sistema cobre desde a criação de pedidos até auditorias e análises, garantindo robustez e flexibilidade no fluxo de mensagens.
