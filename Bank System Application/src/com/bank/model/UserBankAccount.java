package com.bank.model;

public class UserBankAccount {
    private final String username;
    private final Integer bankAccountID;
    private final BankAccountType type;
    private final BankAccountStatus status;
    private final Integer balance;
    private final Currency currency;
    private final String createdAt;

    public UserBankAccount(String username, Integer bankAccountID, BankAccountType type, Currency currency,
                           BankAccountStatus status, Integer balance, String createdAt) {
        this.username = username;
        this.bankAccountID = bankAccountID;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public Integer getBankAccountID() {
        return bankAccountID;
    }

    public BankAccountType getType() {
        return type;
    }

    public BankAccountStatus getStatus() {
        return status;
    }

    public Integer getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "This Bank Account of type (" + type.name + ")\n" +
                "The status of this bank account: (" + status + ")\n" +
                "The account's balance: (" + balance + currency.getSymbol() + ")\n" +
                "This bank account is created at: (" + createdAt + ")\n";
    }
}
