# 🛒 **E-commerce com Apache Kafka — Projeto FIAP**

Projeto desenvolvido em **Java + Spring Boot** demonstrando o uso do **Apache Kafka** para processamento assíncrono e comunicação entre microsserviços em um ambiente de e-commerce.

---

## 👥 **Integrantes do Grupo**

- **Arthur Ramos Dos Santos** — RM558798  
- **Cauã Sanches de Santana** — RM558317  
- **Felipe Melo e Sousa** — RM556099  
- **Heitor Romero da Rocha** — RM558301  

---

## ⚙️ **Arquitetura**

A aplicação é baseada em **eventos Kafka** e implementa dois produtores e dois consumidores diferentes.

### **Produtores**
- **OrderProducer** — Publica mensagens no tópico `orders` quando um novo pedido é criado.  
- **PaymentProducer** — Publica mensagens no tópico `payments` simulando uma integração com um gateway de pagamento.

### **Consumidores**
- **OrderConsumer** — Recebe pedidos do tópico `orders` e simula o processamento, podendo acionar endpoints externos ou APIs REST.  
- **NotificationConsumer** — Recebe informações do tópico `payments` e simula o envio de e-mails de confirmação para o cliente (como se chamasse um servidor de e-mail externo).

---

## 🧩 **Fluxo do Sistema**

1. O cliente envia um **POST** para `/api/orders`.  
2. O **OrderProducer** publica o pedido no tópico `orders`.  
3. O **OrderConsumer** consome essa mensagem e processa o pedido.  
4. Após processar, o sistema aciona o **PaymentProducer**, que publica a informação no tópico `payments`.  
5. O **NotificationConsumer** consome o pagamento e simula o envio de uma notificação (ex: e-mail).  
6. Os pedidos podem ser consultados via **GET `/api/orders`**.

---

## 🧠 **Conceitos Aplicados**

- Comunicação assíncrona com **Apache Kafka**  
- **Produtores e consumidores independentes**  
- Serialização de objetos em **JSON**  
- **Spring Boot + Spring Kafka**  
- Processamento orientado a eventos  
- Simulação de integrações externas (API / e-mail)

---

## 🛠️ **Requisitos**

- **Java 17+**  
- **Docker e Docker Compose**  
- **Gradle**

---

## 🚀 **Como Executar o Projeto**

### 1. Subir a infraestrutura do Kafka
```bash
docker-compose up -d
```

### 2. Verificar se os serviços estão rodando
```bash
docker-compose ps
```

### 3. Acessar as interfaces
- **Kafka UI:** http://localhost:8090  
- **API:** http://localhost:8080/api/orders  

### 4. Executar a aplicação
```bash
./gradlew bootRun
```

---

## 🧪 **Testando a API**

### Criar um novo pedido (Producer)
```bash
curl -X POST http://localhost:8080/api/orders   -H "Content-Type: application/json"   -d '{
    "customerId": "12345",
    "customerEmail": "cliente@exemplo.com",
    "items": [
      {"productName": "Notebook Dell", "quantity": 1, "price": 3500.00},
      {"productName": "Mouse Logitech", "quantity": 2, "price": 150.00}
    ]
  }'
```

### Consultar pedidos (Consumer)
```bash
curl http://localhost:8080/api/orders
```

---

## 🧾 **Estrutura do Projeto**

```
src/main/java/br/com/fiap/ecommerce/
├── config/             # Configuração do Kafka
├── controller/         # Endpoints REST
├── model/              # Modelos de dados (Order, Item)
├── producer/           # Produtores Kafka (Order e Payment)
├── consumer/           # Consumidores Kafka (Order e Notification)
└── EcommerceApplication.java
```

---

## 💡 **Resumo**

Este projeto demonstra o uso de **Apache Kafka** como mensageria central em um sistema de e-commerce distribuído.  
A aplicação processa pedidos e pagamentos de forma **assíncrona**, simulando chamadas externas e automação de notificações.
