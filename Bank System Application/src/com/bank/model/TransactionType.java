package com.bank.model;

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer");
    public final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
