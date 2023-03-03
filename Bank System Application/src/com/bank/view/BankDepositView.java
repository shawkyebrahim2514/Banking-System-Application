package com.bank.view;

import com.bank.model.BankUtil;

public class BankDepositView {
    public static String takeMoney() {
        System.out.print("Enter money to deposit to your bank account(-1 to exit): ");
        return BankUtil.scanner.nextLine();
    }
}
