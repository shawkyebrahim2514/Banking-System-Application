import com.bank.controller.BankApplication;
import com.bank.model.BankUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        BankApplication app = new BankApplication();
        app.run();
    }
}

