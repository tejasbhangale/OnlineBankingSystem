CREATE TABLE lmsUsers(
	unm  VARCHAR(20) PRIMARY KEY,
	pwd  VARCHAR(20) NOT NULL,
	role VARCHAR(20) NOT NULL
);

INSERT INTO lmsUsers VALUES('admin','admin-123','admin');
INSERT INTO lmsUsers VALUES('stud1','stud1-123','student');

CREATE TABLE lmsStudents(
	studid VARCHAR(20) PRIMARY KEY REFERENCES lmsUsers(unm),
	name VARCHAR(20)
);

INSERT INTO lmsStudents VALUES('stud1','Sravan');

CREATE TABLE lmsBooks(
	bcode VARCHAR(20) PRIMARY KEY,
	title VARCHAR(120) NOT NULL,
	status VARCHAR(50) NOT NULL
);

CREATE TABLE lmsRegister(
	logid number(5) PRIMARY KEY,
	bcode VARCHAR(20) REFERENCES lmsBooks(bcode),
	studid VARCHAR(20) REFERENCES lmsStudents(studid),
	resvdt date,
	isudt date,
	rtndt date
);

CREATE SEQUENCE logid_seq START WITH 1 INCREMENT BY 1;