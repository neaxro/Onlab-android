-- Role-ok letrehozasa
insert into Roles values
	('Owner'),
	('Admin'),
	('User')

-- Pelda oraberek
insert into dbo.Wages values
	('Everybody', 0, 1),
	('Default', 1300, 1)

-- Allapotok letrehozasa
insert into States values
	('Elbírálásra vár', 'A szolgálatot még nem bírálták el! (Elutasítás/Elfogadás)'),
	('Folyamatban', 'A szolgálat elvégzése folyamatban van...'),
	('Elfogadva', 'A szolgálatot sikeresen elfogadták!'),
	('Elutasítva', 'A szolgálatot elutasították!')