create database bank_db;

use bank_db;


create table users
(
    username varchar(50) not null primary key,
    password varchar(50) not null
);

create table usersInfo
(
    id          int auto_increment primary key,
    username    varchar(50)  not null,
    firstName   varchar(50)  not null,
    lastName    varchar(50)  not null,
    phoneNumber char(11)     not null,
    address     varchar(50)  not null,
    email       varchar(255) not null unique,
    foreign key (username) references users (username)
);

create table bankAccountTypes
(
    id                       int auto_increment primary key,
    name                     varchar(50) not null,
    minimumBalanceInAccount  int         not null,
    fees                     int         not null,
    interest                 int         not null,
    minimumBalanceToInterest int         not null,
    withdrawalLimits         int         not null
);

insert into bankAccountTypes (name, minimumBalanceInAccount, fees, interest, minimumBalanceToInterest,
                              withdrawalLimits)
values ('Basic', 0, 2, 4, 1000, 50),
       ('Saving', 500, 3, 5, 750, 150);

create table status
(
    id   int auto_increment primary key,
    name varchar(10)
);

insert into status (name)
values ('ACTIVE'),
       ('CLOSED'),
       ('PENDING');

create table currencies
(
    id   int auto_increment primary key,
    name varchar(10) not null
);

# Dollar, Euro, Japanese Yen, Great British Pound
insert into currencies(name)
values ('$'),
       ('€'),
       ('¥'),
       ('£');

create table userBankAccount
(
    id         int auto_increment primary key,
    username   varchar(50) not null,
    typeID     int         not null,
    currencyID int         not null,
    statusID   int         not null,
    balance    int         not null,
    createdAt  timestamp default current_timestamp,
    updatedAt  timestamp default current_timestamp on update current_timestamp,
    foreign key (typeID) references bankAccountTypes (id),
    foreign key (username) references users (username),
    foreign key (statusID) references status (id),
    foreign key (currencyID) references currencies (id),
    INDEX idx_usersBankAccount_balance (balance),
    INDEX idx_usersBankAccount_createAt (createdAt)
);

create table transactionTypes
(
    id   int auto_increment primary key,
    name varchar(20) not null
);

insert into transactionTypes(name)
values ('DEPOSIT'),
       ('WITHDRAWAL'),
       ('TRANSFER');

CREATE TABLE transactions
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  varchar(50) NOT NULL,
    amount    INT NOT NULL,
    typeID    int NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (typeID) REFERENCES transactionTypes (id)
);

create table activityTypes
(
    id   int         not null auto_increment primary key,
    name varchar(50) not null
);

insert into activityTypes (name)
values ('LOGIN'),
       ('LOGOUT'),
       ('ViewBankAccounts'),
       ('ViewTransactionHistory'),
       ('ChangePassword'),
       ('UpdatePersonalInfo'),
       ('OpenBankAccount'),
       ('CloseBankAccount'),
       ('DEPOSIT'),
       ('WITHDRAWAL'),
       ('TRANSFER');

CREATE TABLE logs
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   varchar(50) NOT NULL,
    activityID int         NOT NULL,
    createdAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (activityID) REFERENCES activityTypes (id)
);

CREATE TRIGGER update_balance
    AFTER INSERT
    ON userBankAccount
    FOR EACH ROW
BEGIN
    DECLARE interest_amount INT;
    SELECT FLOOR((NEW.balance * bankAccountTypes.interest) / 100)
    INTO interest_amount
    FROM bankAccountTypes
    WHERE bankAccountTypes.id = NEW.typeID
      AND NEW.balance >= bankAccountTypes.minimumBalanceToInterest;
    IF (interest_amount > 0) THEN
        UPDATE userBankAccount SET balance = balance + interest_amount WHERE id = NEW.id;
    END IF;
END;

CREATE EVENT update_balance_event
    ON SCHEDULE
        EVERY 1 YEAR
            STARTS NOW()
    DO
    CALL update_balance;