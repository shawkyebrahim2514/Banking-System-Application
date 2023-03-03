package com.bank.view;

import com.bank.model.BankUtil;
import com.bank.model.User;

public class BankUserProfileView {
    public static void showProfileFunctions() {
        BankUtil.createHeader("Profile functions");
        BankUtil.createOrderedList(new String[]{
                "Change my profile information",
                "View my bank accounts",
                "Open new bank account",
                "View my log",
                "Logout"
        });
    }

    public static void showWelcome(User user) {
        BankUtil.createHeader("Welcome, " + user.getFirstName() + " " + user.getLastName());
    }

    public static String takeChoice() {
        BankUtil.showTakeFunctionNumber();
        return BankUtil.scanner.nextLine();
    }
}
