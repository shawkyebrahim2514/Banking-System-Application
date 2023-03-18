package com.bank.controller;

import com.bank.model.BankWithdrawalModel;
import com.bank.model.UserBankAccount;
import com.bank.view.BankWithdrawalView;

import java.sql.SQLException;

public class BankWithdrawal {
    private BankWithdrawal() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        if (userBankAccount.getWithdrawalLimit() == 0) {
            BankWithdrawalView.showWithdrawalEnd();
            return;
        }
        Integer withdrawalMoney = takeMoneyToWithdraw(userBankAccount);
        // if user want to exit this action
        if (withdrawalMoney.equals(-1)) return;
        BankWithdrawalModel.withdraw(withdrawalMoney, userBankAccount);
    }

    private static Integer takeMoneyToWithdraw(UserBankAccount userBankAccount) {
        String withdrawalMoney;
        do {
            withdrawalMoney = BankWithdrawalView.takeMoney();
            // if user want to exit this action
            if (withdrawalMoney.equals("-1")) return -1;
        } while (!withdrawalMoney.matches("[0-9]+") ||
                Integer.parseInt(withdrawalMoney) > userBankAccount.getBalance());
        return Integer.valueOf(withdrawalMoney);
    }
}
