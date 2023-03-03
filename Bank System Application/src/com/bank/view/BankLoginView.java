package com.bank.view;

import com.bank.model.BankUtil;

public class BankLoginView {
    public static String[] displayLoginWidow() {
        String[] credentials = new String[2];
        System.out.print("Enter your username: ");
        credentials[0] = BankUtil.scanner.nextLine();
        System.out.print("Enter your password: ");
        credentials[1] = BankUtil.scanner.nextLine();
        return credentials;
    }

    public static void showWrongPassword() {
        BankUtil.createMessage("Username or password isn't correct!");
    }
}
