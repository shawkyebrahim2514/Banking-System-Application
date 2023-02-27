package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankTransactions {
    private BankTransactions() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        // insert this log
        BankUtil.insertIntoLogTable(new Log(
                userBankAccount.getUsername(), ActivityType.ViewTransactionHistory, null
        ));
        // the run body
        getAllTransactions(userBankAccount);
    }

    private static void getAllTransactions(UserBankAccount userBankAccount) throws SQLException {
        List<Transaction> transactions = collectTransactions(userBankAccount);
        BankUtil.createHeader("My transactions");
        for (int i = 0; i < transactions.size(); ++i) {
            BankUtil.createMessage("Account number (" + (i + 1) + ")");
            System.out.println(transactions.get(i));
        }
    }

    private static List<Transaction> collectTransactions(UserBankAccount userBankAccount) throws SQLException {
        List<Transaction> transactions = new LinkedList<>();
        String SQLStatement = "SELECT * FROM transactions WHERE username = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, userBankAccount.getUsername());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Integer amount = result.getInt("amount");
            Integer typeID = result.getInt("typeID");
            String date = result.getString("createdAt");
            transactions.add(new Transaction(
                    userBankAccount.getUsername(), amount, getTransactionType(typeID), date
            ));
        }
        return transactions;
    }

    private static TransactionType getTransactionType(Integer typeID) {
        TransactionType[] transactionTypes = TransactionType.values();
        return transactionTypes[typeID - 1];
    }
}
