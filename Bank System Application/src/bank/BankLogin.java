package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankLogin {
    private BankLogin() {
    }

    protected static void run() throws SQLException {
        String username, password;
        System.out.print("Enter your username: ");
        username = BankUtil.scanner.nextLine();
        System.out.print("Enter your password: ");
        password = BankUtil.scanner.nextLine();
        checkLogin(username, password);
    }

    private static void checkLogin(String username, String password) throws SQLException {
        ResultSet result = executeSQLStatementForUsername(username);
        if (!result.next() || !result.getString("password").equals(password)) {
            BankUtil.createMessage("Username or password isn't correct!");
        } else {
            User user = collectUserData(username, password);
            BankUserProfile.run(user);
        }
    }

    private static ResultSet executeSQLStatementForUsername(String username) throws SQLException {
        String SQLStatement = "SELECT * FROM users WHERE username = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, username);
        return statement.executeQuery();
    }

    private static User collectUserData(String username, String password) throws SQLException {
        User user;
        String SQLStatement = "SELECT * FROM usersInfo WHERE username = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            user = new User(username, password, result.getString("firstName"),
                    result.getString("lastName"), result.getString("phoneNumber"),
                    result.getString("address"), result.getString("email"));
            return user;
        }
        return null;
    }
}
