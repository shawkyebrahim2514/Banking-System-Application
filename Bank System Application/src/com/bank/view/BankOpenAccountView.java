package com.bank.view;

import com.bank.model.BankUtil;

public class BankOpenAccountView {
    public static String takeType() {
        System.out.print("Enter the bank type number: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeCurrency() {
        System.out.print("Enter the currency number: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeInitialBalance() {
        System.out.print("Enter the initial balance for this bank account: ");
        return BankUtil.scanner.nextLine();
    }
}
