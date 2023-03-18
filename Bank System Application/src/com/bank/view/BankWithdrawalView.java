package com.bank.view;

import com.bank.model.BankUtil;

public class BankWithdrawalView {
    public static String takeMoney() {
        System.out.print("Enter money to withdraw(-1 to exit): ");
        return BankUtil.scanner.nextLine();
    }

    public static void showWithdrawalEnd(){
        BankUtil.createMessage("The number of monthly withdrawals has end!");
    }
}
