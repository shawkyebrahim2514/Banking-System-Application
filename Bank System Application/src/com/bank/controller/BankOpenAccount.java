package com.bank.controller;

import com.bank.model.*;
import com.bank.view.BankOpenAccountView;

import java.sql.SQLException;

public class BankOpenAccount {
    private BankOpenAccount() {
    }

    protected static void run(User user) throws SQLException {
        UserBankAccount newAccount;
        int typeID, currencyID, statusID, balance;
        BankAccountType bankAccountType;
        Currency currencyType;
        BankAccountStatus bankAccountStatusType;
        typeID = takeAccountType();
        bankAccountType = BankAccountType.typeInIndex(typeID - 1);
        currencyID = takeAccountCurrency();
        currencyType = Currency.typeInIndex(currencyID - 1);
        statusID = BankAccountStatus.PENDING.ordinal() + 1;
        bankAccountStatusType = BankAccountStatus.typeInIndex(statusID - 1);
        balance = takeBalance(bankAccountType);
        newAccount = new UserBankAccount(user.getUsername(), null, bankAccountType, currencyType,
                bankAccountStatusType, balance, null, null);
        BankOpenAccountModel.saveNewAccount(user, newAccount);
    }

    private static Integer takeAccountType() {
        BankAccountType.showAll();
        String typeID;
        do {
            typeID = BankOpenAccountView.takeType();
        } while (!typeID.matches("[1-2]"));
        return Integer.valueOf(typeID);
    }

    private static Integer takeAccountCurrency() {
        Currency.showAll();
        String currencyID;
        do {
            currencyID = BankOpenAccountView.takeCurrency();
        } while (!currencyID.matches("[1-4]"));
        return Integer.valueOf(currencyID);
    }

    private static Integer takeBalance(BankAccountType bankAccountType) {
        String balance;
        do {
            balance = BankOpenAccountView.takeInitialBalance();
        } while (!balance.matches("[0-9]+") || !bankAccountType.checkValidBalance(Integer.valueOf(balance)));
        return Integer.valueOf(balance);
    }
}
