
CREATE DATABASE IF NOT EXISTS SecurityApp;

USE SecurityApp;

create TABLE IF NOT EXISTS People
(
	`ID` int primary key,
	`Name` nvarchar(30),
	`Username` nvarchar(30) unique,
	`Nickname` nvarchar(30),
	`Email` nvarchar(50),
	`Password` nvarchar(30),
	`ProfilePicture` blob
);

create table IF NOT EXISTS Jobs
(
	`ID` int primary key,
	`Pin` varchar(6),
	`Title` nvarchar(30) unique,
	`Description` nvarchar(150),
	`PeopleID` int references People(ID)
);

create table IF NOT EXISTS Wages
(
	`ID` int primary key,
	`Name` nvarchar(30),
	`Price` real,
	`JobID` int references Jobs(ID)
);


create table IF NOT EXISTS States
(
	ID int primary key,
	Title nvarchar(40) unique,
	Description nvarchar(100)
);

create table IF NOT EXISTS Shifts
(
	`ID` int primary key,
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
	`ID` int primary key,
	`Title` nvarchar(40),
	`Message` text,
	`CreationTime` datetime default CURRENT_TIMESTAMP,
	`JobID` int references Jobs(ID),
	`PeopleID` int references People(ID),
	`WageID` int references Wages(ID)
);

create table IF NOT EXISTS Roles
(
	`ID` int primary key,
	`Title` nvarchar(30) unique
);

create table IF NOT EXISTS Positions
(
	`ID` int primary key,
	`Time` datetime,
	`Longitude` real,
	`Latitude` real,
	`PeopleID` int references People(ID),
	`JobID` int references Jobs(ID)
);

create table IF NOT EXISTS PeopleJobs
(
	`ID` int primary key,
	`JobID` int references Jobs(ID),
	`PeopleID` int references People(ID),
	`RoleID` int references Roles(ID),
	`WageID` int references Wages(ID)
);

/*declare result int;

SELECT @result := 1       
    FROM INFORMATION_SCHEMA.STATISTICS
	 WHERE TABLE_SCHEMA='SecurityApp'
	 AND TABLE_NAME='Jobs'
	 AND INDEX_NAME='IX_Jobs_Pin';
	 
	 SELECT result;*/

-- IF result IS null THEN
CREATE INDEX IX_Jobs_Pin ON Jobs (Pin);
-- END IF;

CREATE INDEX IF NOT EXISTS IX_People_Name ON People (Name);
CREATE INDEX IF NOT EXISTS IX_People_Nickname ON People (Nickname);

CREATE INDEX IX_PeopleJobs_PersonID ON PeopleJobs (PeopleID);
CREATE INDEX IX_PeopleJobs_JobID ON PeopleJobs (JobID);

CREATE INDEX IX_Shifts_JobID ON Shifts (JobID);
CREATE INDEX IX_Shifts_PeopleID ON Shifts (PeopleID);

CREATE INDEX IX_Wages_JobID ON Wages (JobID);


-- Role-ok letrehozasa
insert into Roles VALUES
	('Owner'),
	('Admin'),
	('User');

-- Allapotok letrehozasa
insert into States values
	('Elbiralasra val', '...'),
	('Folyamatban', '...'),
	('Elfogadva', '...'),
	('Elutasitva', '...');