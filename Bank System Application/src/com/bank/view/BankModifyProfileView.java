package com.bank.view;

import com.bank.model.BankUtil;

public class BankModifyProfileView {
    public static void showChangeOptions() {
        BankUtil.createHeader("Change profile info list");
        BankUtil.createOrderedList(new String[]{
                "Change password",
                "Change name",
                "Change phone number",
                "Change address",
                "Change email",
                "Exit"
        });
    }

    public static String takeModifyOption() {
        BankUtil.showTakeFunctionNumber();
        return BankUtil.scanner.nextLine();
    }

    public static String takePassword() {
        System.out.print("Enter a new password: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeFirstName() {
        System.out.print("Enter a new first name: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeLastName() {
        System.out.print("Enter a new last name: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takePhoneNumber() {
        System.out.print("Enter a new phone number: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeAddress() {
        System.out.print("Enter a new address: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeEmail() {
        System.out.print("Enter a new email: ");
        return BankUtil.scanner.nextLine();
    }

}
