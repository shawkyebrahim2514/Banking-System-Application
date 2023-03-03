package com.bank.controller;

import com.bank.model.BankCloseAccountModel;
import com.bank.model.UserBankAccount;
import com.bank.view.BankCloseAccountView;

import java.sql.SQLException;

public class BankCloseAccount {
    private BankCloseAccount() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        BankCloseAccountModel.closeAccount(userBankAccount);
        BankCloseAccountView.showSuccessfulClosing();
    }
}
