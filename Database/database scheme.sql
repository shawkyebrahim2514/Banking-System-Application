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
    withdrawalLimit          int         not null
);

insert into bankAccountTypes (name, minimumBalanceInAccount, fees, interest, minimumBalanceToInterest,
                              withdrawalLimit)
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
    id     int auto_increment primary key,
    symbol varchar(3)  not null,
    name   varchar(20) not null
);

# Dollar, Euro, Japanese Yen, Great British Pound
insert into currencies(name, symbol)
values ('Dollar', '$'),
       ('Euro', '€'),
       ('Japanese yen', '¥'),
       ('Great British Pound', '£');

create table userBankAccount
(
    id                       int auto_increment primary key,
    username                 varchar(50) not null,
    typeID                   int         not null,
    currencyID               int         not null,
    statusID                 int         not null,
    balance                  int         not null,
    createdAt                timestamp            default current_timestamp,
    withdrawalLimit          int         not null,
    lastResetWithdrawalLimit timestamp   not null default current_timestamp,
    updatedAt                timestamp            default current_timestamp on update current_timestamp,
    foreign key (typeID) references bankAccountTypes (id),
    foreign key (username) references users (username),
    foreign key (statusID) references status (id),
    foreign key (currencyID) references currencies (id),
    INDEX idx_balance (balance),
    INDEX idx_lastResetWithdrawalLimit (lastResetWithdrawalLimit)
);

CREATE EVENT reset_withdrawal_limit
    ON SCHEDULE
        EVERY 1 DAY
            STARTS CURRENT_TIMESTAMP
    DO
    UPDATE userBankAccount
    SET withdrawalLimit          = (SELECT withdrawalLimit FROM bankAccountTypes WHERE id = typeID),
        lastResetWithdrawalLimit = current_timestamp
    WHERE lastResetWithdrawalLimit <= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY);


CREATE TRIGGER set_default_withdrawalLimit
    BEFORE INSERT
    ON userBankAccount
    FOR EACH ROW
BEGIN
    SET NEW.withdrawalLimit = (SELECT withdrawalLimit FROM bankAccountTypes WHERE id = NEW.typeID);
END;

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
    amount    INT         NOT NULL,
    typeID    int         NOT NULL,
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

-- insert changing of password in logs
CREATE TRIGGER insert_changePassword_log
    AFTER UPDATE
    ON users
    FOR EACH ROW
BEGIN
    insert into logs(username, activityID) values (new.username, 5);
END;

-- insert changing of personal info in logs
CREATE TRIGGER insert_changePersonalInfo_log
    AFTER UPDATE
    ON usersInfo
    FOR EACH ROW
BEGIN
    insert into logs(username, activityID) values (new.username, 6);
END;

-- insert open a new bank account in logs
CREATE TRIGGER insert_openBankAccount_log
    AFTER INSERT
    ON userBankAccount
    FOR EACH ROW
BEGIN
    insert into logs(username, activityID) values (new.username, 7);
END;

-- insert close a bank account in logs (if the status id is for closed status)
CREATE TRIGGER insert_closeBankAccount_log
    AFTER UPDATE
    ON userBankAccount
    FOR EACH ROW
BEGIN
    if (new.statusID = 2) then
        insert into logs(username, activityID) values (new.username, 8);
    end if;
END;