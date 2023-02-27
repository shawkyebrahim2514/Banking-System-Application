package bank;

import bank.structure.ActivityType;
import bank.structure.Log;
import bank.structure.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankLogs {
    private BankLogs() {
    }

    protected static void run(User user) throws SQLException {
        List<Log> logs = collectLogs(user);
        for (int i = 0; i < logs.size(); ++i) {
            BankUtil.createMessage("Log number (" + (i + 1) + ")");
            System.out.println(logs.get(i));
        }
    }

    private static List<Log> collectLogs(User user) throws SQLException {
        List<Log> logs = new LinkedList<>();
        String SQLStatement = "SELECT activityID, createdAt FROM logs WHERE username = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, user.getUsername());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Integer activityID = result.getInt("activityID");
            String createdAt = result.getString("createdAt");
            Log log = new Log(user.getUsername(), getActivityType(activityID), createdAt);
            logs.add(log);
        }
        return logs;
    }

    private static ActivityType getActivityType(Integer activityID) {
        ActivityType[] activityTypes = ActivityType.values();
        return activityTypes[activityID - 1];
    }
}
