IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].PeopleJobs') AND type in (N'U'))
		drop table PeopleJobs

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].Dashboard') AND type in (N'U'))
		drop table Dashboard

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].Shifts') AND type in (N'U'))
		drop table Shifts

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].Positions') AND type in (N'U'))
		drop table Positions

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].Jobs') AND type in (N'U'))
		drop table Jobs

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].People') AND type in (N'U'))
		drop table People

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].Wages') AND type in (N'U'))
		drop table Wages

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].States') AND type in (N'U'))
		drop table States

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].Roles') AND type in (N'U'))
		drop table Roles

create table [People]
(
	ID int identity primary key,
	Name nvarchar(30),
	Username nvarchar(20) unique,
	Nickname nvarchar(30),
	Email nvarchar(20),
	Password nvarchar(30),
	ProfilePicture image
)

create table [Wages]
(
	ID int identity primary key,
	Name nvarchar(30) unique,
	Price real
)

create table [Jobs]
(
	ID int identity primary key,
	Pin varchar(6),
	Title nvarchar(20) unique,
	Description nvarchar(100),
	PeopleID int references People(ID) --creator
)

create table [States]
(
	ID int identity primary key,
	Title nvarchar(20) unique,
	Description nvarchar(100)
)

create table [Shifts]
(
	ID int identity primary key,
	StartTime datetime2 default GETDATE(),
	EndTime datetime2,
	EarnedMoney real,
	PeopleID int references People(ID),
	JobID int references Jobs(ID),
	WageID int references Wages(ID),
	StatusID int references States(ID)
)

create table [Dashboard]
(
	ID int identity primary key,
	Title nvarchar(20),
	Message text,
	CreationTime datetime2 default GETDATE(),
	JobID int references Jobs(ID),
	PeopleID int references People(ID),
	WageID int references Wages(ID)
)

create table [Roles]
(
	ID int identity primary key,
	Title nvarchar(20) unique
)

create table [Positions]
(
	ID int identity primary key,
	Time datetime2,
	Longitude real,
	Latitude real,
	PeopleID int references People(ID)
)

create table [PeopleJobs]
(
	ID int identity primary key,
	JobID int references Jobs(ID),
	PeopleID int references People(ID),
	RoleID int references Roles(ID),
	WageID int references Wages(ID)
)