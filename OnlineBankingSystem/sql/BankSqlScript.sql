CREATE TABLE Account_Master(Account_ID NUMBER(10) primary key, Account_Type VARCHAR2(25), Account_Balance NUMBER(15) ,Open_Date DATE);

CREATE TABLE Customer(Account_ID NUMBER(10) references Account_Master(Account_ID), customer_name VARCHAR2(50), Email VARCHAR2(30), Address VARCHAR2(100), Pancard VARCHAR2(15));

CREATE TABLE Transactions(Transaction_ID NUMBER(5) primary key,Tran_description VARCHAR2(100), DateofTransaction DATE , TransactionType VARCHAR2(1), TranAmount NUMBER(15) ,Account_ID NUMBER(10) references Account_Master(Account_ID));

CREATE TABLE Service_Tracker(Service_ID NUMBER primary key, Service_Description VARCHAR2(100),Account_ID NUMBER references Account_Master(Account_ID), Service_Raised_Date DATE ,Service_status VARCHAR2(20));

CREATE TABLE User_Table(Account_ID NUMBER references Account_Master(Account_ID),user_id NUMBER primary key,login_password VARCHAR2(15),secret_question VARCHAR2(50),Transaction_password VARCHAR2(15),lock_status VARCHAR2(1));

CREATE TABLE Fund_Transfer(FundTransfer_ID NUMBER primary key,Account_ID NUMBER(10) references Account_Master(Account_ID),Payee_Account_ID NUMBER(10), Date_Of_Transfer DATE, Transfer_Amount NUMBER(15));

CREATE TABLE PayeeTable(Account_ID NUMBER(10) references Account_Master(Account_ID),Payee_Account_ID NUMBER(10) primary key , Nick_name VARCHAR2(40));

CREATE TABLE ADMIN_TABLE(ADMIN_ID NUMBER(3) primary key,USER_ID VARCHAR2(20),PASSWORD VARCHAR(20));

INSERT INTO ADMIN_TABLE VALUES(1,'admin','admin');

INSERT INTO Account_Master Values(1001,'Savings',50000,'12-JAN-2013');
INSERT INTO Account_Master Values(1002,'Current',70000,'1-DEC-2007');
INSERT INTO Account_Master Values(1003,'Current',100000,'23-OCT-1996');
INSERT INTO Account_Master Values(1004,'Savings',150000,'17-MAR-1999');
INSERT into ACCOUNT_MASTER values(1005,'Savings',45000,'8-SEP-2001');
