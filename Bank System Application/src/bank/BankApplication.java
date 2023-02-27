package bank;

import java.sql.SQLException;

public class BankApplication {
    private boolean state;

    public BankApplication() {
        state = true;
    }

    public void run() throws SQLException {
        do {
            mainWindow();
        } while (state);
        BankUtil.scanner.close();
        BankUtil.connection.close();
    }

    private void mainWindow() throws SQLException {
        showMainWindowOptions();
        takeMainWindowOption();
    }

    private void showMainWindowOptions() {
        BankUtil.createHeader("What do you want to do?");
        BankUtil.createOrderedList(new String[]{
                "Login", "Register", "Exit"
        });
    }

    private void takeMainWindowOption() throws SQLException {
        String option;
        do {
            BankUtil.showTakeFunctionNumber();
            option = BankUtil.scanner.nextLine();
        } while (!option.matches("[1-3]"));
        runMainWindowOption(Integer.valueOf(option));
    }

    private void runMainWindowOption(Integer option) throws SQLException {
        switch (option) {
            case 1 -> BankLogin.run();
            case 2 -> BankRegistration.run();
            case 3 -> state = false;
        }
    }
}
