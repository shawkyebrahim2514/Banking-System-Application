package com.bank.model;

public class Transaction {
    private final String username;
    private final Integer amount;
    private final TransactionType type;
    private final String createdAt;

    public Transaction(String username, Integer amount, TransactionType type, String createdAt) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public TransactionType getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "This transaction of type (" + type.name + ")\n" +
                "Transaction amount: (" + amount + ")\n" +
                "This transaction is created at: (" + createdAt + ")\n";
    }
}
