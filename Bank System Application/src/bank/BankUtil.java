package bank;

import bank.structure.Log;
import bank.structure.Transaction;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class BankUtil {
    private BankUtil() {
    }

    protected static Scanner scanner;
    protected static Connection connection;

    static {
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

    protected static Boolean checkEmail(String email) {
        if (email.length() > 255 || email.matches("^[\\\\w\\-\\\\.]+@([\\\\w-]+\\\\.)+[\\\\w-]{2,4}$")) {
            BankUtil.createMessage("Email isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    protected static Boolean checkAddress(String address) {
        if (address.length() > 50) {
            createMessage("Address isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    protected static Boolean checkPhoneNumber(String phoneNumber) {
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

    protected static Boolean checkName(String name) {
        if (name.length() > 50) {
            createMessage("Name isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    protected static Boolean checkPassword(String password) {
        if (password.length() > 50) {
            createMessage("Password isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    protected static void insertIntoLogTable(Log newLog) throws SQLException {
        String SQLStatement = "insert into logs (username, activityID) VALUES (?, ?)";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, newLog.getUsername());
        statement.setString(2, String.valueOf(newLog.getType().ordinal() + 1));
        statement.executeUpdate();
    }

    protected static void insertIntoTransactionsTable(Transaction transaction) throws SQLException {
        String SQLStatement = "insert into transactions (username, amount, typeID) VALUES (?, ?, ?)";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, transaction.getUsername());
        statement.setString(2, Integer.toString(transaction.getAmount()));
        statement.setString(3, String.valueOf(transaction.getType().ordinal() + 1));
        statement.executeUpdate();
    }

    public static void createMessage(String paragraph) {
        int width = 200;
        int part = (width / 2) - (paragraph.length() / 2);
        String bothSides = "-".repeat(part);
        String spaceArea = " ".repeat(3);
        System.out.println(bothSides + spaceArea + paragraph + spaceArea + bothSides);
    }

    protected static void createHeader(String paragraph) {
        int width = 200;
        int part = ((width / 2) - (paragraph.length() / 2));
        String rightSide = "<".repeat(part);
        String leftSide = ">".repeat(part);
        String spaceArea = " ".repeat(3);
        System.out.println(rightSide + spaceArea + paragraph + spaceArea + leftSide);
    }

    protected static void createOrderedList(String[] list) {
        for (int i = 0; i < list.length; ++i) {
            System.out.println((i + 1) + "- " + list[i]);
        }
    }

    protected static void showTakeFunctionNumber() {
        System.out.println("Enter function number from the list: ");
    }
}
