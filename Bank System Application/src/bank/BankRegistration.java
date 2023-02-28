package bank;

import bank.structure.BankAccountType;
import bank.structure.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankRegistration {
    private BankRegistration() {
    }

    protected static void run() throws SQLException {
        register();
    }

    private static ResultSet executeSQLStatementForUsername(String username) throws SQLException {
        String SQLStatement = "SELECT * FROM Users WHERE username = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, username);
        return statement.executeQuery();
    }

    private static void register() throws SQLException {
        String username, password, phoneNumber, address, email;
        String[] fullName;
        User user;
        username = registerUsername();
        password = registerPassword();
        fullName = registerName();
        phoneNumber = registerPhoneNumber();
        address = registerAddress();
        email = registerEmail();
        user = new User(username, password, fullName[0], fullName[1], phoneNumber, address, email);
        saveNewUser(user);
    }

    private static String registerUsername() throws SQLException {
        String username;
        do {
            System.out.print("Enter your username(at most 50 characters): ");
            username = BankUtil.scanner.nextLine();
        } while (!checkUsername(username));
        return username;
    }

    private static Boolean checkUsername(String username) throws SQLException {
        if (username.length() > 50 || !checkUniqueUsername(username)) {
            BankUtil.createMessage("This username already exist or isn't in the correct format!");
            return false;
        } else {
            return true;
        }
    }

    private static Boolean checkUniqueUsername(String username) throws SQLException {
        ResultSet result = executeSQLStatementForUsername(username);
        return !result.next();
    }

    private static String registerPassword() {
        String password;
        do {
            System.out.print("Enter your password(at most 50 characters): ");
            password = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkPassword(password));
        return password;
    }

    private static String[] registerName() {
        String[] fullName = new String[2];
        String name;
        do {
            System.out.print("Enter your first name(at most 50 characters): ");
            name = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkName(name));
        fullName[0] = name;

        do {
            System.out.print("Enter your last name(at most 50 characters): ");
            name = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkName(name));
        fullName[1] = name;
        return fullName;
    }

    private static String registerPhoneNumber() {
        String phoneNumber;
        do {
            System.out.print("Enter your phoneNumber: ");
            phoneNumber = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    private static String registerAddress() {
        String address;
        do {
            System.out.print("Enter your address(at most 50 characters): ");
            address = BankUtil.scanner.nextLine();
        } while (!BankUtil.checkAddress(address));
        return address;
    }

    private static String registerEmail() throws SQLException {
        String email;
        boolean uniqueEmail;
        do {
            System.out.print("Enter your email: ");
            email = BankUtil.scanner.nextLine();
            uniqueEmail = checkUniqueEmail(email);
            if (!uniqueEmail) {
                BankUtil.createMessage("This email is used before!");
            }
        } while (!BankUtil.checkEmail(email) || !uniqueEmail);
        return email;
    }

    private static boolean checkUniqueEmail(String email) throws SQLException {
        String SQLStatement = "SELECT * FROM usersInfo WHERE email = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, email);
        ResultSet result = statement.executeQuery();
        return !result.next();
    }

    private static void saveNewUser(User user) throws SQLException {
        insertIntoUsersTable(user);
        insertIntoUsersInfoTable(user);
    }

    private static Integer insertIntoUsersTable(User user) throws SQLException {
        String SQLStatement = "insert into users (username, password) values (?, ?)";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        return statement.executeUpdate();
    }

    private static void insertIntoUsersInfoTable(User user) throws SQLException {
        String SQLStatement = "insert into usersInfo (username, firstName, lastName, phoneNumber, address, email) " +
                "values (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, user.getAddress());
        statement.setString(6, user.getEmail());
        statement.executeUpdate();
    }
}