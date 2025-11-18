# Payment Events Module

Domain events for event-driven architecture in Payment Chain platform.

## Purpose

This module contains all domain events published by microservices for async communication via message brokers (Kafka/RabbitMQ).

## Events Catalog

### Transaction Events
- `TransactionCreatedEvent` - New transaction created (Builder pattern ✅)
- `TransactionSettledEvent` - Transaction completed
- `TransactionFailedEvent` - Transaction processing failed

### Customer Events
- `CustomerCreatedEvent` - New customer registered
- `CustomerUpdatedEvent` - Customer information updated

### Notification Events
- `NotificationRequestedEvent` - Notification needs to be sent

## Usage

### Add Dependency
```xml
<dependency>
    <groupId>com.paymentchain</groupId>
    <artifactId>payment-events</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Publishing Events (with Builder Pattern)
```java
// Create event using builder
TransactionCreatedEvent event = TransactionCreatedEvent.builder()
    .transactionId(transaction.getId())
    .reference(transaction.getReference())
    .accountIban(transaction.getAccountIban())
    .amount(transaction.getAmount())
    .currency("EUR")
    .status(transaction.getStatus())
    .channel("WEB")
    .description(transaction.getDescription())
    .transactionDate(LocalDateTime.now())
    .build();

// Publish to Kafka
kafkaTemplate.send("transaction.created", event);
```

### Consuming Events
```java
@KafkaListener(topics = "transaction.created")
public void handleTransactionCreated(TransactionCreatedEvent event) {
    log.info("Received transaction: {}", event.getTransactionId());
    log.info("Amount: {} {}", event.getAmount(), event.getCurrency());
    // Process event
}
```

### JSON Serialization
All events are JSON-serializable with Jackson:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JavaTimeModule());

// Serialize
String json = mapper.writeValueAsString(event);

// Deserialize
TransactionCreatedEvent event = mapper.readValue(json, TransactionCreatedEvent.class);
```

## Event Design Principles

1. **Immutable**: Events cannot be modified after creation
2. **Self-contained**: Include all necessary information
3. **Versioned**: Each event has unique ID and timestamp (from DomainEvent base class)
4. **JSON-serializable**: Compatible with Kafka and REST APIs
5. **Builder Pattern**: Fluent API for easy construction

## Base Event Class

All events extend `DomainEvent` from payment-domain module:
- `eventId`: Unique UUID for each event
- `occurredOn`: Timestamp when event occurred
- `eventType`: Simple class name

## Testing

```bash
mvn test
```

## Build

```bash
mvn clean install
```

## Author

**Benas** - Fintech Platform Developer
