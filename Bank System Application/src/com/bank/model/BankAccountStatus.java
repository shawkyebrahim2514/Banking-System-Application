package com.bank.model;

public enum BankAccountStatus {
    ACTIVE("Active"),
    CLOSED("Closed"),
    PENDING("Pending");

    private final String name;

    public static BankAccountStatus typeInIndex(Integer index) {
        return BankAccountStatus.values()[index];
    }

    BankAccountStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
