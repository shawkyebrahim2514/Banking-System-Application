package com.bank.view;

import com.bank.model.BankUtil;
import com.bank.model.UserBankAccount;

import java.util.List;

public class BankViewAccountsView {
    public static String takeChoice() {
        BankUtil.showTakeFunctionNumber();
        return BankUtil.scanner.nextLine();
    }

    public static String takeAccountNumber() {
        System.out.print("Enter bank account number from the list to do this function on: ");
        return BankUtil.scanner.nextLine();
    }

    public static void showEmptyAccounts() {
        BankUtil.createMessage("You don't have any account yet!");
    }

    public static void showInactiveAccount() {
        BankUtil.createMessage("All your bank accounts are inactive, you cannot do anything here!");
    }

    public static void showUserBankAccountFunctions() {
        BankUtil.createHeader("What do you want to do?");
        BankUtil.createOrderedList(new String[]{
                "Deposit",
                "Withdraw",
                "Transfer",
                "Show transactions",
                "Close bank account",
                "Exit"
        });
    }

    public static void printAccounts(List<UserBankAccount> userBankAccounts) {
        BankUtil.createHeader("My Bank Accounts");
        for (int i = 0; i < userBankAccounts.size(); ++i) {
            BankUtil.createMessage("Account number (" + (i + 1) + ")");
            System.out.println(userBankAccounts.get(i));
        }
    }
}
