package bank;

import bank.structure.ActivityType;
import bank.structure.Log;
import bank.structure.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankChangeUserProfileInfo {
    private BankChangeUserProfileInfo() {
    }

    protected static void run(User user) throws SQLException {
        showChangeOptions();
        takeChangeOption(user);
    }

    private static void showChangeOptions() {
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

    private static void takeChangeOption(User user) throws SQLException {
        String option;
        do {
            BankUtil.showTakeFunctionNumber();
            option = BankUtil.scanner.nextLine();
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

    private static void updateTable(String tableName, String columnToSet, String newValue, String columnToSearch,
                                    String valueToSearch) throws SQLException {
        String SQLStatement = "UPDATE " + tableName + " SET " + columnToSet + " = ? WHERE "
                + columnToSearch + " = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, newValue);
        statement.setString(2, valueToSearch);
        statement.executeUpdate();
    }

    private static void changePassword(User user) throws SQLException {
        String password;
        do {
            System.out.print("Enter a new password: ");
            password = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkPassword(password));
        updateTable("users", "password", password, "username",
                user.getUsername());
    }

    private static void changeName(User user) throws SQLException {
        String firstName, lastName;
        do {
            System.out.print("Enter a new first name: ");
            firstName = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkName(firstName));
        do {
            System.out.print("Enter a new second name: ");
            lastName = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkName(lastName));
        updateTable("usersInfo", "firstName", firstName, "userID",
                user.getUsername());
        updateTable("usersInfo", "lastName", lastName, "userID",
                user.getUsername());
    }

    private static void changePhoneNumber(User user) throws SQLException {
        String phoneNumber;
        do {
            System.out.print("Enter a new phone number: ");
            phoneNumber = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkPhoneNumber(phoneNumber));
        updateTable("usersInfo", "phoneNumber", phoneNumber, "userID",
                user.getUsername());
    }

    private static void changeAddress(User user) throws SQLException {
        String address;
        do {
            System.out.print("Enter a new address: ");
            address = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkAddress(address));
        updateTable("usersInfo", "address", address, "userID",
                user.getUsername());
    }

    private static void changeEmail(User user) throws SQLException {
        String email;
        do {
            System.out.print("Enter a new email: ");
            email = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkEmail(email));
        updateTable("usersInfo", "email", email, "userID",
                user.getUsername());
    }
}