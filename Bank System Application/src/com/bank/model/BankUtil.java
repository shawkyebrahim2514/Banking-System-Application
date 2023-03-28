package com.bank.model;

import com.bank.controller.BankException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class BankUtil {
    private BankUtil() {
    }

    public static Scanner scanner;
    public static Connection connection;

    static{
        scanner = new Scanner(System.in);
        try {
            String url = "jdbc:mysql://localhost:3306/bank_db";
            String username = "root";
            String password = "shawky";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean checkEmail(String email) {
        if (email.length() > 255 || email.matches("^[\\\\w\\-\\\\.]+@([\\\\w-]+\\\\.)+[\\\\w-]{2,4}$")) {
            BankUtil.createMessage("Email isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static Boolean checkAddress(String address) {
        if (address.length() > 50) {
            createMessage("Address isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static Boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11 || !phoneNumber.matches("[0-9]{11}")) {
            return false;
        }
        String[] validPrefixes = {"010", "011", "012", "015"};
        String prefix = phoneNumber.substring(0, 3);
        if (!Arrays.asList(validPrefixes).contains(prefix)) {
            createMessage("Phone number isn't in the correct format!");
            return false;
        }
        return true;
    }

    public static Boolean checkName(String name) {
        if (name.length() > 50) {
            createMessage("Name isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static Boolean checkPassword(String password) {
        if (password.length() > 50) {
            createMessage("Password isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    public static void insertIntoLogTable(Log newLog) throws SQLException {
        try {
            String SQLStatement = "call insertLog(?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQLStatement);
            statement.setString(1, newLog.getUsername());
            statement.setString(2, String.valueOf(newLog.getType().ordinal() + 1));
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    public static void insertIntoTransactionsTable(Transaction transaction) throws SQLException {
        try {
            String SQLStatement = "call insertTransaction(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQLStatement);
            statement.setString(1, transaction.getUsername());
            statement.setInt(2, transaction.getAmount());
            statement.setInt(3, transaction.getType().ordinal() + 1);
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    public static void createMessage(String paragraph) {
        int width = 200;
        int part = (width / 2) - (paragraph.length() / 2);
        String bothSides = "-".repeat(part);
        String spaceArea = " ".repeat(3);
        System.out.println(bothSides + spaceArea + paragraph + spaceArea + bothSides);
    }

    public static void createHeader(String paragraph) {
        int width = 200;
        int part = ((width / 2) - (paragraph.length() / 2));
        String rightSide = "<".repeat(part);
        String leftSide = ">".repeat(part);
        String spaceArea = " ".repeat(3);
        System.out.println(rightSide + spaceArea + paragraph + spaceArea + leftSide);
    }

    public static void createOrderedList(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            System.out.println((i + 1) + "- " + list[i]);
        }
    }

    public static void showTakeFunctionNumber() {
        System.out.print("Enter function number from the list: ");
    }
}
