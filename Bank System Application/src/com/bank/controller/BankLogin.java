package com.bank.controller;

import com.bank.model.User;
import com.bank.view.BankLoginView;
import com.bank.model.BankLoginModel;
import java.sql.SQLException;

public class BankLogin {
    private BankLogin() {
    }

    public static void run() throws SQLException {
        String[] credentials = BankLoginView.displayLoginWidow();
        String username = credentials[0];
        String password = credentials[1];
        checkLogin(username, password);
    }

    private static void checkLogin(String username, String password) throws SQLException {
        String resultPassword = BankLoginModel.getPasswordOf(username);
        if (resultPassword == null || !resultPassword.equals(password)) {
            BankLoginView.showWrongPassword();
        } else {
            User user = BankLoginModel.collectUserData(username, password);
            BankUserProfile.run(user);
        }
    }
}
