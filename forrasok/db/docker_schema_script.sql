SELECT 'Adatbazis letrehozasa...' AS 'Feladatok: 1/6';
CREATE DATABASE IF NOT EXISTS SecurityApp;
USE SecurityApp;

SELECT 'Tablak torlese...' AS 'Feladatok: 2/6';
DROP TABLE IF EXISTS People;
DROP TABLE IF EXISTS Jobs;
DROP TABLE IF EXISTS Wages;
DROP TABLE IF EXISTS States;
DROP TABLE IF EXISTS Shifts;
DROP TABLE IF EXISTS Dashboard;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS Positions;
DROP TABLE IF EXISTS PeopleJobs;

SELECT 'Tablak letrehozasa...' AS 'Feladatok: 3/6';
create TABLE IF NOT EXISTS People
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(30),
	`Username` nvarchar(30) unique,
	`Nickname` nvarchar(30),
	`Email` nvarchar(50),
	`Password` nvarchar(30),
	`ProfilePicture` blob
);

create table IF NOT EXISTS Jobs
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Pin` varchar(6),
	`Title` nvarchar(30) unique,
	`Description` nvarchar(150),
	`PeopleID` int references People(ID)
);

create table IF NOT EXISTS Wages
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(30),
	`Price` real,
	`JobID` int references Jobs(ID)
);


create table IF NOT EXISTS States
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Title` nvarchar(40) unique,
	`Description` nvarchar(100)
);

create table IF NOT EXISTS Shifts
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`StartTime` datetime default CURRENT_TIMESTAMP,
	`EndTime` datetime,
	`EarnedMoney` real,
	`PeopleID` int references People(ID),
	`JobID` int references Jobs(ID),
	`WageID` int references Wages(ID),
	`StatusID` int references States(ID)
);

create table IF NOT EXISTS Dashboard
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Title` nvarchar(40),
	`Message` text,
	`CreationTime` datetime default CURRENT_TIMESTAMP,
	`JobID` int references Jobs(ID),
	`PeopleID` int references People(ID),
	`WageID` int references Wages(ID)
);

create table IF NOT EXISTS Roles
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Title` nvarchar(30) unique
);

create table IF NOT EXISTS Positions
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`Time` datetime,
	`Longitude` real,
	`Latitude` real,
	`PeopleID` int references People(ID),
	`JobID` int references Jobs(ID)
);

create table IF NOT EXISTS PeopleJobs
(
	`ID` int primary KEY NOT NULL AUTO_INCREMENT,
	`JobID` int references Jobs(ID),
	`PeopleID` int references People(ID),
	`RoleID` int references Roles(ID),
	`WageID` int references Wages(ID)
);

SELECT 'Indexek letrehozasa...'  AS 'Feladatok: 4/6';
CREATE INDEX IX_Jobs_Pin ON Jobs (Pin);
CREATE INDEX IX_People_Name ON People (Name);
CREATE INDEX IX_People_Nickname ON People (Nickname);
CREATE INDEX IX_PeopleJobs_PersonID ON PeopleJobs (PeopleID);
CREATE INDEX IX_PeopleJobs_JobID ON PeopleJobs (JobID);
CREATE INDEX IX_Shifts_JobID ON Shifts (JobID);
CREATE INDEX IX_Shifts_PeopleID ON Shifts (PeopleID);
CREATE INDEX IX_Wages_JobID ON Wages (JobID);

SELECT 'Role-ok letrehozasa...' AS 'Feladatok: 5/6';
INSERT INTO Roles(`Title`)
VALUES
	('Owner'),
	('Admin'),
	('User');

SELECT 'Allapotok letrehozasa...' AS 'Feladatok: 6/6';
insert into States (`Title`, `Description`)
VALUES
	('Elbiralasra var', '...'),
	('Folyamatban', '...'),
	('Elfogadva', '...'),
	('Elutasitva', '...');