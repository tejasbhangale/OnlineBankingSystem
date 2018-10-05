

CREATE TABLE Account_Master(Account_ID NUMBER(10) primary key,User_ID NUMBER(10) references User_table(user_ID), Account_Type VARCHAR2(25), Account_Balance NUMBER(13,2) ,Open_Date DATE);

CREATE TABLE Customer(User_ID NUMBER references User_table(user_ID), customer_name VARCHAR2(50), mobile number(10),Email VARCHAR2(30), Address VARCHAR2(100), Pancard VARCHAR2(15));

CREATE TABLE Transactions(Transaction_ID NUMBER(5) primary key,Tran_description VARCHAR2(100), DateofTransaction DATE , TransactionType VARCHAR2(1), TranAmount NUMBER(15) ,Account_ID NUMBER(10) references Account_Master(Account_ID));

CREATE TABLE Service_Tracker(Service_ID NUMBER primary key, Service_Description VARCHAR2(100),Account_ID NUMBER references Account_Master(Account_ID), Service_Raised_Date DATE ,Service_status VARCHAR2(40));

CREATE TABLE User_Table(user_id NUMBER primary key,login_password VARCHAR2(15),secret_answer VARCHAR2(50),secret_question VARCHAR2(50),Transaction_password VARCHAR2(15),lock_status VARCHAR2(1));

CREATE TABLE Fund_Transfer(FundTransfer_ID NUMBER primary key,Account_ID NUMBER(10) references Account_Master(Account_ID),Payee_Account_ID NUMBER(10), Date_Of_Transfer DATE, Transfer_Amount NUMBER(15));

CREATE TABLE PayeeTable(Account_ID NUMBER(10) references Account_Master(Account_ID),Payee_Account_ID NUMBER(10) primary key , Nick_name VARCHAR2(40));

CREATE TABLE ADMIN_TABLE(ADMIN_ID NUMBER(3) primary key,USER_ID VARCHAR2(20),PASSWORD VARCHAR(20));

INSERT INTO ADMIN_TABLE VALUES(1,'admin','admin');

INSERT INTO Account_Master Values(1001,120,'Savings',50000,'12-JAN-2013');
INSERT INTO Account_Master Values(1002,125,'Current',70000,'1-DEC-2007');
INSERT INTO Account_Master Values(1003,170,'Current',100000,'23-OCT-1996');
INSERT INTO Account_Master Values(1004,130,'Savings',150000,'17-MAR-1999');

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

INSERT INTO User_Table Values(120,'loginpwd','Pitbul','What is your pet name','tranpwd','l');
INSERT INTO User_Table Values(125,'loginpwd','Scarlet','What is your pet name','tranpwd','l');
INSERT INTO User_Table Values(170,'loginpwd','Fluffy','What is your pet name','tranpwd','l');
INSERT INTO User_Table Values(130,'loginpwd','Teju','What is your pet name','tranpwd','l');


create sequence service
START WITH 1005
INCREMENT BY 1
NOCACHE;

CREATE sequence fund_transfer_seq
START WITH 4000
INCREMENT BY 1
NOCACHE;

ALTER TABLE User_table DROP CONSTRAINT PRIMARY KEY;

ALTER TABLE Customer DROP CONSTRAINT FOREIGN KEY;

ALTER TABLE customer RENAME COLUMN Account_ID user_ID NUMBER;

ALTER TABLE customer ADD FOREIGN KEY (user_ID) REFERENCES User_table(user_ID);

DELETE FROM customer ;

INSERT INTO Fund_Transfer()