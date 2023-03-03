package com.bank.model;

import java.sql.SQLException;

public class BankUserProfileModel {
    public static void insertLoginLog(String username) throws SQLException {
        BankUtil.insertIntoLogTable(new Log(
                username, ActivityType.LOGIN, null
        ));
    }

    public static void insertLogoutLog(String username) throws SQLException {
        BankUtil.insertIntoLogTable(new Log(
                username, ActivityType.LOGOUT, null
        ));
    }
}
