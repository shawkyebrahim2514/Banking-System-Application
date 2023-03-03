package com.bank.controller;

import com.bank.model.*;
import com.bank.view.BankViewAccountsView;

import java.sql.SQLException;
import java.util.List;

public class BankViewAccounts {
    private BankViewAccounts() {
    }

    protected static void run(User user) throws SQLException {
        BankViewAccountModel.insertLog(user.getUsername());
        int functionNumber, userBankAccountNumber;
        List<UserBankAccount> userBankAccounts = showUserBankAccounts(user);
        boolean hasActiveAccounts = false;
        if(userBankAccounts.isEmpty()){
            BankViewAccountsView.showEmptyAccounts();
            return;
        }
        for (UserBankAccount userBankAccount : userBankAccounts) {
            if (userBankAccount.getStatus().equals(BankAccountStatus.ACTIVE)) {
                hasActiveAccounts = true;
            }
        }
        if (!hasActiveAccounts) {
            BankViewAccountsView.showInactiveAccount();
            return;
        }
        do {
            BankViewAccountsView.showUserBankAccountFunctions();
            functionNumber = takeUserBankAccountFunction();
            if (functionNumber == 6) break;
            userBankAccountNumber = takeUserBankAccountNumber(userBankAccounts);
            runUserBankAccountFunction(functionNumber, userBankAccounts.get(userBankAccountNumber - 1));
        } while (true);
    }

    private static List<UserBankAccount> showUserBankAccounts(User user) throws SQLException {
        List<UserBankAccount> userBankAccounts = BankViewAccountModel.collectUserBankAccounts(user);
        BankViewAccountsView.printAccounts(userBankAccounts);
        return userBankAccounts;
    }


    private static Integer takeUserBankAccountFunction() {
        String choice;
        do {
            choice = BankViewAccountsView.takeChoice();
        } while (!choice.matches("[1-6]"));
        return Integer.valueOf(choice);
    }

    private static Integer takeUserBankAccountNumber(List<UserBankAccount> userBankAccounts) {
        String userBankAccountNumber;
        do {
            userBankAccountNumber = BankViewAccountsView.takeAccountNumber();
        } while (!userBankAccountNumber.matches("[1-" + userBankAccounts.size() + "]") ||
                !checkBankAccountActive(userBankAccounts.get(Integer.parseInt(userBankAccountNumber) - 1)));
        return Integer.valueOf(userBankAccountNumber);
    }

    private static boolean checkBankAccountActive(UserBankAccount userBankAccount) {
        if (userBankAccount.getStatus().equals(BankAccountStatus.ACTIVE)) {
            return true;
        } else {
            BankViewAccountsView.showInactiveAccount();
            return false;
        }
    }

    private static void runUserBankAccountFunction(Integer choice, UserBankAccount userBankAccount)
            throws SQLException {
        switch (choice) {
            case 1 -> BankDeposit.run(userBankAccount);
            case 2 -> BankWithdrawal.run(userBankAccount);
            case 3 -> BankTransfer.run(userBankAccount);
            case 4 -> BankTransactions.run(userBankAccount);
            case 5 -> BankCloseAccount.run(userBankAccount);
        }
    }

}