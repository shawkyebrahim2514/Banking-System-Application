package com.bank.controller;

import com.bank.model.BankUserProfileModel;
import com.bank.model.User;
import com.bank.view.BankUserProfileView;

import java.sql.SQLException;

public class BankUserProfile {
    private BankUserProfile() {
    }

    protected static void run(User user) throws SQLException {
        BankUserProfileModel.insertLoginLog(user.getUsername());
        BankUserProfileView.showWelcome(user);
        Integer choice;
        do {
            BankUserProfileView.showProfileFunctions();
            choice = takeProfileFunctionsChoice(user);
        } while (!choice.equals(5));
    }

    private static Integer takeProfileFunctionsChoice(User user) throws SQLException {
        String choice;
        do {
            choice = BankUserProfileView.takeChoice();
        } while (!choice.matches("[1-5]"));
        runProfileFunction(user, Integer.valueOf(choice));
        return Integer.valueOf(choice);
    }

    private static void runProfileFunction(User user, Integer choice) throws SQLException {
        switch (choice) {
            case 1 -> BankModifyProfile.run(user);
            case 2 -> BankViewAccounts.run(user);
            case 3 -> BankOpenAccount.run(user);
            case 4 -> BankLogs.run(user);
            case 5 -> logout(user);
        }
    }

    private static void logout(User user) throws SQLException {
        BankUserProfileModel.insertLogoutLog(user.getUsername());
    }
}
