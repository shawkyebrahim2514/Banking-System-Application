package com.bank.controller;

import com.bank.model.*;
import com.bank.view.BankDepositView;

import java.sql.SQLException;

public class BankDeposit {
    private BankDeposit() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        Integer addedMoney = takeMoneyToDeposit();
        // if user want to exit this action
        if (addedMoney.equals(-1)) return;
        BankDepositModel.deposit(addedMoney, userBankAccount);
    }

    private static Integer takeMoneyToDeposit() {
        String addedMoney;
        do {
            addedMoney = BankDepositView.takeMoney();
            // if user want to exit this action
            if (addedMoney.equals("-1")) return -1;
        } while (!addedMoney.matches("[0-9]+"));
        return Integer.valueOf(addedMoney);
    }

}
