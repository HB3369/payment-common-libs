package com.paymentchain.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paymentchain.events.transaction.TransactionCreatedEvent;
import com.paymentchain.events.transaction.TransactionSettledEvent;
import com.paymentchain.events.transaction.TransactionFailedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for transaction events serialization/deserialization.
 *
 * @author benas
 */
class TransactionEventsTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldCreateTransactionCreatedEvent() {
        // Arrange & Act
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
            .transactionId(1L)
            .reference("TX001")
            .accountIban("ES1234567890123456789012")
            .amount(new BigDecimal("100.50"))
            .currency("EUR")
            .status("PENDING")
            .channel("WEB")
            .description("Test transaction")
            .transactionDate(LocalDateTime.now())
            .build();

        // Assert
        assertThat(event).isNotNull();
        assertThat(event.getEventId()).isNotNull();
        assertThat(event.getOccurredOn()).isNotNull();
        assertThat(event.getEventType()).isEqualTo("TransactionCreatedEvent");
        assertThat(event.getTransactionId()).isEqualTo(1L);
        assertThat(event.getReference()).isEqualTo("TX001");
        assertThat(event.getAmount()).isEqualByComparingTo(new BigDecimal("100.50"));
    }

    @Test
    void shouldSerializeTransactionCreatedEventToJson() throws Exception {
        // Arrange
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
            .transactionId(1L)
            .reference("TX001")
            .accountIban("ES1234567890123456789012")
            .amount(new BigDecimal("100.50"))
            .currency("EUR")
            .status("PENDING")
            .channel("WEB")
            .description("Test transaction")
            .transactionDate(LocalDateTime.of(2024, 11, 18, 10, 30))
            .build();

        // Act
        String json = objectMapper.writeValueAsString(event);

        // Assert
        assertThat(json).isNotNull();
        assertThat(json).contains("TX001");
        assertThat(json).contains("ES1234567890123456789012");
        assertThat(json).contains("100.50");
        assertThat(json).contains("EUR");
    }

    @Test
    void shouldDeserializeTransactionCreatedEventFromJson() throws Exception {
        // Arrange
        String json = """
            {
                "transactionId": 1,
                "reference": "TX001",
                "accountIban": "ES1234567890123456789012",
                "amount": 100.50,
                "currency": "EUR",
                "status": "PENDING",
                "channel": "WEB",
                "description": "Test transaction",
                "transactionDate": "2024-11-18T10:30:00"
            }
            """;

        // Act
        TransactionCreatedEvent event = objectMapper.readValue(json, TransactionCreatedEvent.class);

        // Assert
        assertThat(event).isNotNull();
        assertThat(event.getTransactionId()).isEqualTo(1L);
        assertThat(event.getReference()).isEqualTo("TX001");
        assertThat(event.getAccountIban()).isEqualTo("ES1234567890123456789012");
        assertThat(event.getAmount()).isEqualByComparingTo(new BigDecimal("100.50"));
        assertThat(event.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void shouldHaveUniqueEventIdsForDifferentEvents() {
        // Arrange & Act
        TransactionCreatedEvent event1 = TransactionCreatedEvent.builder()
            .transactionId(1L)
            .reference("TX001")
            .accountIban("ES1234567890123456789012")
            .amount(new BigDecimal("100"))
            .currency("EUR")
            .build();

        TransactionCreatedEvent event2 = TransactionCreatedEvent.builder()
            .transactionId(2L)
            .reference("TX002")
            .accountIban("ES9876543210987654321098")
            .amount(new BigDecimal("200"))
            .currency("EUR")
            .build();

        // Assert
        assertThat(event1.getEventId()).isNotEqualTo(event2.getEventId());
    }

    @Test
    void shouldIncludeOccurredOnTimestamp() {
        // Arrange & Act
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
            .transactionId(1L)
            .reference("TX001")
            .accountIban("ES1234567890123456789012")
            .amount(new BigDecimal("100"))
            .currency("EUR")
            .build();

        // Assert
        assertThat(event.getOccurredOn()).isNotNull();
        assertThat(event.getOccurredOn()).isBeforeOrEqualTo(java.time.Instant.now());
    }
}
