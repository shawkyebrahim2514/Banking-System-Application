package com.bank.controller;

import com.bank.model.BankModifyProfileModel;
import com.bank.model.BankUtil;
import com.bank.model.User;
import com.bank.view.BankModifyProfileView;

import java.sql.SQLException;

public class BankModifyProfile {
    private BankModifyProfile() {
    }

    protected static void run(User user) throws SQLException {
        BankModifyProfileView.showChangeOptions();
        takeChangeOption(user);
    }

    private static void takeChangeOption(User user) throws SQLException {
        String option;
        do {
            option = BankModifyProfileView.takeModifyOption();
        } while (!option.matches("[1-6]"));
        runChangeOption(user, Integer.valueOf(option));
    }

    private static void runChangeOption(User user, Integer option) throws SQLException {
        switch (option) {
            case 1 -> changePassword(user);
            case 2 -> changeName(user);
            case 3 -> changePhoneNumber(user);
            case 4 -> changeAddress(user);
            case 5 -> changeEmail(user);
        }
    }

    private static void changePassword(User user) throws SQLException {
        String password;
        do {
            password = BankModifyProfileView.takePassword();
        } while (!BankUtil.checkPassword(password));
        BankModifyProfileModel.updatePassword(password, user.getUsername());
    }

    private static void changeName(User user) throws SQLException {
        String firstName, lastName;
        do {
            firstName = BankModifyProfileView.takeFirstName();
        } while (!BankUtil.checkName(firstName));
        do {
            lastName = BankModifyProfileView.takeLastName();
        } while (!BankUtil.checkName(lastName));
        BankModifyProfileModel.updateProfileInfo("firstName", firstName, user.getUsername());
        BankModifyProfileModel.updateProfileInfo("lastName", lastName, user.getUsername());
    }

    private static void changePhoneNumber(User user) throws SQLException {
        String phoneNumber;
        do {
            phoneNumber = BankModifyProfileView.takePhoneNumber();
        } while (!BankUtil.checkPhoneNumber(phoneNumber));
        BankModifyProfileModel.updateProfileInfo("phoneNumber", phoneNumber, user.getUsername());
    }

    private static void changeAddress(User user) throws SQLException {
        String address;
        do {
            address = BankModifyProfileView.takeAddress();
        } while (!BankUtil.checkAddress(address));
        BankModifyProfileModel.updateProfileInfo("address", address, user.getUsername());
    }

    private static void changeEmail(User user) throws SQLException {
        String email;
        do {
            email = BankModifyProfileView.takeEmail();
        } while (!BankUtil.checkEmail(email));
        BankModifyProfileModel.updateProfileInfo("email", email, user.getUsername());
    }
}