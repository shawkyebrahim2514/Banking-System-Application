package com.bank.view;

import com.bank.model.BankUtil;

public class BankApplicationView {
    public static void displayMainWindow() {
        BankUtil.createHeader("What do you want to do?");
        BankUtil.createOrderedList(new String[]{
                "Login", "Register", "Exit"
        });
    }

    public static String takeOption() {
        BankUtil.showTakeFunctionNumber();
        return BankUtil.scanner.nextLine();
    }
}
