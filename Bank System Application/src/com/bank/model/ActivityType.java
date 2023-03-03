package com.bank.model;

public enum ActivityType {
    LOGIN("Login"),
    LOGOUT("Logout"),
    ViewBankAccounts("Viewing bank accounts"),
    ViewTransactionHistory("Viewing transaction history"),
    ChangePassword("Changing password"),
    UpdatePersonalInfo("Updating personal information"),
    OpenBankAccount("Openning new bank account"),
    CloseBankAccount("Closing bank account"),
    DEPOSIT("Making a deposit"),
    WITHDRAWAL("Making withdrawal"),
    TRANSFER("Making transferring");
    public final String name;

    ActivityType(String name) {
        this.name = name;
    }
}
