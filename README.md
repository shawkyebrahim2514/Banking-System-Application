# Banking System Application

The Bank System Application is a Java-based project that implements the principles of object-oriented programming and clean code to create a banking system. The system uses a MySQL database to store and load user data.

## File Structure

The project consists of several classes organized into different packages:

* `enums`: This package contains the following enum classes:

  * `ActivityType`: Contains all possible user activities, used for logging purposes.
  * `BankAccountStatus`: Contains possible status of a bank account (Active, Closed, or Pending).
  * `BankAccountType`: Contains the two types of bank accounts (basic or saving).
  * `Currency`: Contains the allowed currency types in the bank (Dollar, Euro, Japanese Yen, Great British Pound).
  * `TransactionType`: Contains all transaction types (Deposit, Withdrawal, Transfer).

* `models`: This package contains the following model classes:
  * `Log`: Used to store log data for printing or storing in the database.
  * `Transaction`: Used to store transaction data for printing or storing in the database.
  * `User`: Used to store user data for printing or storing in the database.
  * `UserBankAccount`: Used to store user bank account data for printing or storing in the database.

* `util`: This package contains the following utility classes:
  * `BankUtil`: Contains utility methods for performing various banking operations.

* `views`: This package contains the following view classes:
  * `BankApplication`: The main class that users can instantiate to execute the banking application.
  * `BankChangeUserProfileInfo`: Handles functions for changing user profile information.
  * `BankCloseAccount`: Handles functions for closing a bank account.
  * `BankDeposi`t: Handles functions for depositing money into a bank account.
  * `BankLogin`: Handles functions for user login.
  * `BankLogs`: Handles functions for displaying user logs.
  * `BankOpenAccount`: Handles functions for opening a new bank account.
  * `BankRegistration`: Handles functions for user registration.
  * `BankTransactions`: Handles functions for displaying user transactions.
  * `BankTransfer`: Handles functions for transferring money between bank accounts.
  * `BankUserProfile`: Handles functions for displaying user profile information.
  * `BankViewAccounts`: Handles functions for displaying user bank accounts.
 
 
 
## Enum Classes

### `ActivityType`
This enum class contains all possible user activities that are used for logging purposes. The activities include:
* Login
* Logout
* View bank account
* View transaction history
* Change password
* Update personal information
* Open new bank account
* Close bank account
* Deposit
* Withdrawal
* Transfer

### `BankAccountStatus`
This enum class contains the possible status of a bank account, which can be one of the following:
* Active
* Closed
* Pending

### `BankAccountType`
This enum class contains the two types of bank accounts:
* Basic bank account
  * Minimum balance to be in account: 0
  * Fees: 2%
  * Interest: 4%
  * Minimum balance to get interest: 1000
  * Number of withdrawal limits per month: 50

* Saving bank account
  * Minimum balance to be in account: 500
  * Fees: 3%
  * Interest: 5%
  * Minimum balance to get interest: 750
  * Number of withdrawal limits per month: 150
  
### `Currency`
This enum class contains the allowed currency types in the bank:
* Dollar
* Euro
* Japanese Yen
* Great British Pound


## BankUserProfile Class
The BankUserProfile class is responsible for handling all functions related to the user's profile. When the user logs in, they are redirected to their profile. In the user's profile, they can perform the following functions:
* Change Profile Information: This function is handled by the BankChangeUserProfileInfo class. Users can change their password, first name, last name, phone number, address, and email.
* View Bank Accounts: This function is handled by the BankViewAccounts class. All of the user's bank accounts are displayed in order. The user can select one of their active bank accounts and perform one of the following functions on it: deposit, withdraw, transfer, show transactions, and close bank account.
* Open New Bank Account: This function is handled by the BankOpenAccount class. All bank account types are displayed for the user to choose from. The user must enter the type of account, currency type, and initial balance according to the type of the account.
* View Logs: This function is handled by the BankLogs class. All logs that belong to the user are displayed.

## BankChangeUserProfileInfo Class
The BankChangeUserProfileInfo class is responsible for handling all functions related to changing the user's profile information. Users can change their password, first name, last name, phone number, address, and email.

## BankViewAccounts Class
The BankViewAccounts class is responsible for handling all functions related to viewing the user's bank accounts. All of the user's bank accounts are displayed in order. The user can select one of their active bank accounts and perform one of the following functions on it: deposit, withdraw, transfer, show transactions, and close bank account.

## BankOpenAccount Class
The BankOpenAccount class is responsible for handling all functions related to opening a new bank account. All bank account types are displayed for the user to choose from. The user must enter the type of account, currency type, and initial balance according to the type of the account.

## BankLogs Class
The BankLogs class is responsible for handling all functions related to viewing the user's logs. All logs that belong to the user are displayed.


## In conclusion
This Bank Application project is a comprehensive system that allows users to manage their banking activities such as account management, money transfers, and transaction monitoring. This project has been developed using the Java programming language and the MySQL database management system.

* The application comprises several classes, each responsible for handling specific features of the application. The main class is the BankApplication class, which initializes the application and offers a menu to the user to choose from. The BankLogin class handles user authentication, and the BankRegistration class manages user registration.

* After successful login, the user is directed to the BankUserProfile class, where they can change their personal information, view their bank accounts, open new accounts, and view their transaction logs. The BankChangeUserProfileInfo class handles user profile updates, while the BankViewAccounts class displays a user's accounts and allows them to perform account-related activities such as deposits, withdrawals, and transfers. The BankOpenAccount class handles the process of opening a new bank account, and the BankLogs class displays a user's transaction logs.

* This project is an excellent example of a Java-based application that utilizes various features of the programming language such as object-oriented programming, file handling, and database connectivity. The source code for the project is well-structured and documented, which makes it easy to read and modify.

Overall, this Bank Application project provides a useful tool for managing personal banking activities, and its implementation demonstrates the robustness and versatility of the Java programming language.
