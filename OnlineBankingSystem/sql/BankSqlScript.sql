
drop table  Account_Master cascade constraints;
drop table  Customer cascade constraints;
drop table  Transactions cascade constraints;
drop table  Service_Tracker cascade constraints;
drop table  User_Table cascade constraints;
drop table  Fund_Transfer cascade constraints;
drop table  PayeeTable cascade constraints;
drop table  ADMIN_TABLE cascade constraints;


CREATE TABLE User_Table(user_id NUMBER primary key,login_password VARCHAR2(15),secret_answer VARCHAR2(50),secret_question VARCHAR2(50),Transaction_password VARCHAR2(15),lock_status VARCHAR2(1));

CREATE TABLE Account_Master(Account_ID NUMBER(10) primary key,user_id references user_table(user_id), Account_Type VARCHAR2(25), Account_Balance NUMBER(13,2) ,Open_Date DATE);

CREATE TABLE Customer(user_id references user_table(user_id), customer_name VARCHAR2(50), Mobile NUMBER(10),Email VARCHAR2(30), Address VARCHAR2(100), Pancard VARCHAR2(15));

CREATE TABLE Transactions(Transaction_ID NUMBER(5) primary key,Tran_description VARCHAR2(100), Date_of_Transaction DATE , Transaction_Type VARCHAR2(1), Transfer_Amount NUMBER(13,2) ,Account_ID NUMBER(10) references Account_Master(Account_ID));

CREATE TABLE Service_Tracker(Service_ID NUMBER primary key, Service_Description VARCHAR2(100),Account_ID NUMBER references Account_Master(Account_ID), Service_Raised_Date DATE ,Service_status VARCHAR2(40));

CREATE TABLE Fund_Transfer(Fund_Transfer_ID NUMBER primary key,Account_ID NUMBER(10) references Account_Master(Account_ID),Payee_Account_ID NUMBER(10), Date_Of_Transfer DATE, Transfer_Amount NUMBER(13,2));

CREATE TABLE PayeeTable(Account_ID NUMBER(10) references Account_Master(Account_ID),Payee_Account_ID NUMBER(10) primary key , Nick_name VARCHAR2(40));

CREATE TABLE ADMIN_TABLE(ADMIN_ID NUMBER(3) primary key,ADMIN_USER_ID VARCHAR2(20),ADMIN_PASSWORD VARCHAR(20));

INSERT INTO ADMIN_TABLE VALUES(1,'admin','admin');

INSERT INTO User_Table Values(120,'loginpwd','Pitbul','What is your pet name','tranpwd','l');
INSERT INTO User_Table Values(125,'loginpwd','Scarlet','What is your pet name','tranpwd','l');
INSERT INTO User_Table Values(170,'loginpwd','Fluffy','What is your pet name','tranpwd','l');
INSERT INTO User_Table Values(130,'loginpwd','Teju','What is your pet name','tranpwd','l');

INSERT INTO Account_Master Values(1001,120,'Savings',50000,'12-JAN-2013');
INSERT INTO Account_Master Values(1002,125,'Current',70000,'1-DEC-2007');
INSERT INTO Account_Master Values(1003,170,'Current',100000,'23-OCT-1996');
INSERT INTO Account_Master Values(1004,130,'Savings',150000,'17-MAR-1999');
INSERT INTO Account_Master Values(1005,170,'Current',10000,'23-NOV-1991');
INSERT INTO Account_Master Values(1006,130,'Savings',15000,'17-SEP-2000');
INSERT INTO Account_Master Values(1007,125,'Current',600000,'23-OCT-2003');
INSERT INTO Account_Master Values(1008,125,'Savings',177255,'01-JUN-1992');


INSERT INTO Customer Values(120,'Alice',9957421711,'alice@gmail.com','Whitefeild','dchr22');
INSERT INTO Customer Values(125,'Bob',9682316872,'bob@hotmail.com','Whitefeild','dchr22');
INSERT INTO Customer Values(170,'Andrea',8011744401,'andrea@gmail.com','Whitefeild','dchr22');
INSERT INTO Customer Values(130,'Teja',9405771110,'teja@gmail.com','Whitefeild','dchr22');

INSERT INTO Transactions Values(78965,'SUCCESSFUL','13-JAN-2016','c',5000,1001);
INSERT INTO Transactions Values(52021,'SUCCESSFUL','16-FEB-2019','d',5002,1001);
INSERT INTO Transactions Values(56821,'Failed','18-JAN-2018','d',5040,1001);
INSERT INTO Transactions Values(53698,'SUCCESSFUL','15-DEC-2017','c',5100,1001);

INSERT INTO Service_Tracker Values(150,'Service1',1001,'19-JAN-2018','Done Sevice Request');
INSERT INTO Service_Tracker Values(151,'Service2',1002,'15-AUG-2018','Done Sevice Request');
INSERT INTO Service_Tracker Values(152,'Service3',1003,'20-JAN-2000','Incomplete Sevice Request');
INSERT INTO Service_Tracker Values(153,'Service4',1004,'19-FEB-2015','Done Sevice Request');


create sequence service
START WITH 1005
INCREMENT BY 1
NOCACHE;


CREATE SEQUENCE TRANSFER_SEQ
START WITH 4000
INCREMENT BY 1
NOCACHE;


CREATE SEQUENCE ACCNUM_SEQ
START WITH 1010
INCREMENT BY 1
NOCACHE;

CREATE SEQUENCE USERID_SEQ
START WITH 200
INCREMENT BY 1
NOCACHE;
