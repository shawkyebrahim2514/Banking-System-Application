drop database bank_db;

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

create trigger set_bankAccount_pending_status
    before insert
    on userBankAccount
    for each row
begin
    set new.statusID = 3;
end;

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

create trigger decrease_withdrawalLimit
    before update
    on userBankAccount
    for each row
begin
    if (new.balance < old.balance) then
        set new.withdrawalLimit = new.withdrawalLimit - 1;
    end if;
end;

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

-- ----------------------------- Procedures ----------------------------------
create procedure insertLog(p_username varchar(50), p_activityId int)
begin
    insert into logs (username, activityID) values (p_username, p_activityId);
end;

create procedure insertTransaction(p_username varchar(50), p_amount int, p_typeID int)
begin
    insert into transactions (username, amount, typeID) values (p_username, p_amount, p_typeID);
end;

create procedure updateBankAccountStatus(p_statusID int, p_id int)
begin
    update userBankAccount set statusID = p_statusID where id = p_id;
end;

update userBankAccount
set balance = balance + ?
where id = ?;
create procedure updateBankAccountBalance(p_amount int, p_id int)
begin
    update userBankAccount set balance = balance + p_amount where id = p_id;
end;

create procedure getUserPassword(p_username varchar(50))
begin
    select password from Users WHERE username = p_username;
end;

create procedure getUserInfo(p_username varchar(50))
begin
    select * from usersInfo WHERE username = p_username;
end;

create procedure getNumberOfUserLogs(p_username varchar(50))
begin
    SELECT count(*) as numberOfLogs FROM logs WHERE username = p_username;
end;

create procedure getUserLogs(p_username varchar(50), p_limit int, p_offset int)
begin
    select activityID, createdAt from logs where username = p_username order by id desc limit p_limit offset p_offset;
end;

create procedure updateUserPassword(p_password varchar(50), p_username varchar(50))
begin
    update users set password = p_password where username = p_username;
end;

CREATE PROCEDURE updateUserInfo(
    IN p_columnName varchar(30),
    IN p_columnValue varchar(255),
    IN p_username varchar(50)
)
BEGIN
    SET @query = CONCAT('update usersInfo set ', p_columnName, ' = ?', ' where username = ?');
    PREPARE statement FROM @query;
    SET @columnValue = p_columnValue;
    SET @username = p_username;
    EXECUTE statement USING @columnValue, @username;
    DEALLOCATE PREPARE statement;
END;

create procedure insertBankAccount(p_username varchar(50), p_typeID int, p_currencyID int, p_balance int)
begin
    insert into userBankAccount (username, typeID, currencyID, balance)
    values (p_username, p_typeID, p_currencyID, p_balance);
end;

create procedure checkUniqueEmail(p_email varchar(255))
begin
    select not exists(select email from usersInfo where email = p_email) as isUniqueEmail;
end;

create procedure insertUser(p_username varchar(50), p_password varchar(50))
begin
    insert into users (username, password) values (p_username, p_password);
end;

create procedure insertUserInfo(p_username varchar(50), p_firstName varchar(50), p_lastName varchar(50),
                                p_phoneNumber char(11), p_address varchar(50), p_email varchar(255))
begin
    insert into usersInfo (username, firstName, lastName, phoneNumber, address, email)
    values (p_username, p_firstName, p_lastName, p_phoneNumber, p_address, p_email);
end;

create procedure getUserTransactions(p_username varchar(50), p_limit int, p_offset int)
begin
    select amount, typeID, createdAt
    from transactions
    where username = p_username
    order by id desc
    limit p_limit offset p_offset;
end;

create procedure getNumberOfUserTransactions(p_username varchar(50))
begin
    SELECT count(*) as numberOfTransactions FROM transactions WHERE username = p_username;
end;

create procedure checkValidBankAccount(p_id int)
begin
    select exists(select id from userBankAccount where id = p_id and statusID = 1) as isValidBankAccount;
end;

create procedure getUserBankAccounts(p_username varchar(50))
begin
    select id, typeID, currencyID, statusID, balance, withdrawalLimit, createdAt
    from userBankAccount
    where username = p_username;
end;