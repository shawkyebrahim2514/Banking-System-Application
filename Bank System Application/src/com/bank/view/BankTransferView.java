package com.bank.view;

import com.bank.model.BankUtil;

public class BankTransferView {
    public static String takeMoney() {
        System.out.print("Enter money to be transferred: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeOtherAccount() {
        System.out.print("Enter the ID of the other user bank account(-1 to exit): ");
        return BankUtil.scanner.nextLine();
    }

    public static void showInvalidAccount() {
        BankUtil.createMessage("This bank account ID doesn't exist or isn't active!");
    }

    public static void showSameAccount() {
        BankUtil.createMessage("You cannot transfer to the same bank account!");
    }

}
