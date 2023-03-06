--###############################################################################
-- _____     _     _                 _       _   _               _     
--|  __ \   | |   | |               | |     | | | |             (_)    
--| |__) |__| | __| | __ _  __ _  __| | __ _| |_| |__   __ _ _____ ___ 
--|  ___/ _ \ |/ _` |/ _` |/ _` |/ _` |/ _` | __| '_ \ / _` |_  / / __|
--| |  |  __/ | (_| | (_| | (_| | (_| | (_| | |_| |_) | (_| |/ /| \__ \
--|_|   \___|_|\__,_|\__,_|\__,_|\__,_|\__,_|\__|_.__/ \__,_/___|_|___/
--###############################################################################

-- Pelda emberek
insert into dbo.People values
	('Nemes Axel Roland', 'neaxro', 'Axi', 'neaxro@gmail.com', 'Asdasd11', null),
	('Nagy Béla', 'belus11', 'Béla', 'bela12@gmail.com', 'AS1234555', null),
	('Kiss Emese', 'emike', 'Emese', 'emese99@gmail.com', 'Asdasd11', null),
	('Kovács János', 'janko', 'Jancsi', 'janchi@gmail.com', 'kasjdlaskd2', null),
	('Takács Imre', 'imreee', 'Imi', 'imy56@gmail.com', 'iwerdx87', null)

-- Pelda oraberek
insert into dbo.Wages values
	('Mindenki', 0),
	('Sima', 1300),
	('Speciális', 2300.90),
	('Kutyás', 1890.90),
	('Kerítés', 1590.90)

-- Allapotok letrehozasa
insert into States values
	('Folyamatban', 'A szolgálat elvégzése folyamatban van...'),
	('Befejezve', 'A szolgálatot befejezték'),
	('Elfogadva', 'A szolgálatot sikeresen elfogadták!'),
	('Elutasítva', 'A szolgálatot elutasították!')

-- Role-ok letrehozasa
insert into Roles values
	('Tulajdonos', 1, 1, 1, 0, 1),
	('Admin', 1, 1, 1, 1, 1),
	('Felhasználó', 0, 0, 0, 1, 0)

-- Pelda egy munkara
insert into dbo.Jobs values
	('ASD123', 'FOO 2023', 'Fishing On Orfű 2023', 1)

-- Emberek munkara rendelese
insert into PeopleJobs values
	(1, 1, 1, 2), -- FOO, Nemes Axel Roland, Tulajdonos, Sima
	(1, 2, 2, 2), -- FOO, Nagy Béla, Admin, Sima
	(1, 3, 3, 3), -- FOO, Kiss Emese, Felhasználó, Speciális
	(1, 4, 3, 4), -- FOO, Kovács János, Felhasználó, Kutyás
	(1, 5, 3, 5)  -- FOO, Takács Imre, Felhasználó, Kerítés

-- Dashboard uzenet letrehozasa
DECLARE @JOBID as Int
select @JOBID = ID from Jobs where Title like '%FOO%'

insert into Dashboard (Title, Message, JobID, PeopleID, WageID) values
	('Üdvözlő üzenet', 'Üdvözlök mindenkit a FOO 2023-as munkában!', @JOBID, 1, 1),
	('Kutyás járőrözés', 'Kérem a kutyával járőröző kollégákat a főépületbe!', @JOBID, 2, 4)

-- Szolgalatok peldak
insert into Shifts values
	(GETDATE(), null, null, 3, 1, 3, 1),
	(GETDATE(), GETDATE(), 2400.9, 4, 1, 2, 2),
	(GETDATE(), GETDATE(), 1230.9, 3, 1, 2, 3),
	(GETDATE(), GETDATE(), 11230.9, 3, 1, 4, 4)

--###############################################################################
-- _____     _     _       _      _                _                        _    
--|  __ \   | |   | |     | |    | |              | |                      | |   
--| |__) |__| | __| | __ _| | ___| | _____ _ __ __| | ___ _______  ___  ___| | __
--|  ___/ _ \ |/ _` |/ _` | |/ _ \ |/ / _ \ '__/ _` |/ _ \_  / _ \/ __|/ _ \ |/ /
--| |  |  __/ | (_| | (_| | |  __/   <  __/ | | (_| |  __// /  __/\__ \  __/   < 
--|_|   \___|_|\__,_|\__,_|_|\___|_|\_\___|_|  \__,_|\___/___\___||___/\___|_|\_\
--###############################################################################
--																			figlet
-- Comment:
--					CTRL + K, CTRL + C
-- Uncomment:
--					CTRL + K, CTRL + U
--###############################################################################

--					|Letrehozott munkak listazasa|
--select *
--from Jobs j
--	inner join People p on p.ID = j.PeopleID

--					|Minden illesztes omlesztve|
--select *
--from PeopleJobs pj
--	inner join Jobs j on pj.JobID = j.ID
--	inner join People p on pj.PeopleID = p.ID
--	inner join Roles r on pj.RoleID = r.ID
--	inner join Wages w on pj.WageID = w.ID

--					|Minden illesztes, relevansabb adatok|
--select
--	j.Title as 'Munka címe',
--	j.[Description] as 'Munka rövid leírása',
--	j.Pin as 'Csatlakozási pin',
--	p.[Name] as 'Munkán dolgozó emberek',
--	p.Nickname as 'Becenév',
--	r.Title as 'Beosztás',
--	w.[Name] as 'Órabér neve',
--	w.Wage as 'Órabér (Ft/óra)'
--from PeopleJobs pj
--	inner join Jobs j on pj.JobID = j.ID
--	inner join People p on pj.PeopleID = p.ID
--	inner join Roles r on pj.RoleID = r.ID
--	inner join Wages w on pj.WageID = w.ID

--					|Minden illesztes, emberek rovidebb, relevansabb adatai|
--select
--	j.Title as 'Munka címe',
--	j.[Description] as 'Munka rövid leírása',
--	p.[Name] as 'Tulajdonos neve',
--	p.Nickname as 'Tulajdonos beceneve',
--	r.Title as 'Beosztás'
--from PeopleJobs pj
--	inner join Jobs j on pj.JobID = j.ID
--	inner join People p on pj.PeopleID = p.ID
--	inner join Roles r on pj.RoleID = r.ID
--	inner join Wages w on pj.WageID = w.ID
--where r.ID = 1

--					|Dashboard uzenetek|
--select
--	d.Title,
--	d.Message,
--	p.Name as 'Feladó',
--	w.Name as 'Címzettek'
--from Dashboard d
--	inner join People p on d.PeopleID = p.ID
--	inner join Wages w on d.WageID = w.ID

--					|Dashboard uzenetek CSAK ADOTT FELHASZNALONAK|
--go
--DECLARE @JOBID int
--select @JOBID = ID from Jobs where Title like '%FOO%'

--DECLARE @WAGEID int
--select @WAGEID = pj.WageID

--from PeopleJobs pj
--	inner join People p on pj.PeopleID = p.ID
--where
--	p.Name like '%János%'		-- János, Axel
--	and pj.JobID = @JOBID

--select
--	d.Title,
--	d.Message,
--	p.Name as 'Feladó',
--	w.Name as 'Címzettek'
--from Dashboard d
--	inner join People p on d.PeopleID = p.ID
--	inner join Wages w on d.WageID = w.ID
--where
--	d.WageID in (@WAGEID, 1)		-- 1 az a MINDENKI csatorna

--					|Szolgalatok listazasa|
--select
--	j.Title as 'Munka megnevezése',
--	s.StartTime as 'Szolgálat kezdete',
--	s.EndTime as 'Szolgálat vége',
--	s.EarnedMoney as 'Megszerzett fizetés',
--	p.Name as 'Munkát teljesítő',
--	w.Name as 'Órabér neve',
--	w.Wage as 'Órabér (Ft/óra)',
--	st.Title as 'Szolgálat állapota',
--	st.Description as 'Állapot magyarázata'
--from Shifts s
--	inner join People p on s.PeopleID = p.ID
--	inner join Jobs j on s.JobID = j.ID
--	inner join Wages w on s.WageID = w.ID
--	inner join States  st on s.StatusID = st.ID
--where s.EndTime is not null