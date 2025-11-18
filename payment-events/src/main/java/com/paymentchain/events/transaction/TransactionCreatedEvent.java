package com.paymentchain.events.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paymentchain.domain.model.event.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Event published when a transaction is created.
 *
 * This event triggers:
 * - Notification service (send email/SMS)
 * - Analytics service (track metrics)
 * - Fraud detection service (validate transaction)
 *
 * @author benas
 */
public class TransactionCreatedEvent extends DomainEvent {

    private Long transactionId;
    private String reference;
    private String accountIban;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String channel;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionDate;

    // Constructor sin argumentos (requerido por Jackson)
    public TransactionCreatedEvent() {
        super();
    }

    // Constructor builder-style
    private TransactionCreatedEvent(Builder builder) {
        super();
        this.transactionId = builder.transactionId;
        this.reference = builder.reference;
        this.accountIban = builder.accountIban;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.status = builder.status;
        this.channel = builder.channel;
        this.description = builder.description;
        this.transactionDate = builder.transactionDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Builder Pattern
    public static class Builder {
        private Long transactionId;
        private String reference;
        private String accountIban;
        private BigDecimal amount;
        private String currency;
        private String status;
        private String channel;
        private String description;
        private LocalDateTime transactionDate;

        public Builder transactionId(Long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder accountIban(String accountIban) {
            this.accountIban = accountIban;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public TransactionCreatedEvent build() {
            return new TransactionCreatedEvent(this);
        }
    }

    // Getters
    public Long getTransactionId() {
        return transactionId;
    }

    public String getReference() {
        return reference;
    }

    public String getAccountIban() {
        return accountIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public String getChannel() {
        return channel;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    // Setters (para Jackson deserialization)
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionCreatedEvent{" +
                "eventId='" + getEventId() + '\'' +
                ", transactionId=" + transactionId +
                ", reference='" + reference + '\'' +
                ", accountIban='" + accountIban + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", occurredOn=" + getOccurredOn() +
                '}';
    }
}
