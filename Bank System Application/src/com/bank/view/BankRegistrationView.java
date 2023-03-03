package com.bank.view;

import com.bank.model.BankUtil;

public class BankRegistrationView {
    public static String takeUsername() {
        System.out.print("Enter your username(at most 50 characters): ");
        return BankUtil.scanner.nextLine();
    }

    public static String takePassword() {
        System.out.print("Enter your password(at most 50 characters): ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeFirstName() {
        System.out.print("Enter your first name(at most 50 characters): ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeLastName() {
        System.out.print("Enter your last name(at most 50 characters): ");
        return BankUtil.scanner.nextLine();
    }

    public static String takePhoneNumber() {
        System.out.print("Enter your phoneNumber: ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeAddress() {
        System.out.print("Enter your address(at most 50 characters): ");
        return BankUtil.scanner.nextLine();
    }

    public static String takeEmail() {
        System.out.print("Enter your email: ");
        return BankUtil.scanner.nextLine();
    }

    public static void showWrongUsername() {
        BankUtil.createMessage("This username already exist or isn't in the correct format!");
    }

    public static void showUnUniqueEmail() {
        BankUtil.createMessage("This email is used before!");
    }

}
