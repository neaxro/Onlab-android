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
	('Elb�r�l�sra v�r', 'A szolg�latot m�g nem b�r�lt�k el! (Elutas�t�s/Elfogad�s)'),
	('Folyamatban', 'A szolg�lat elv�gz�se folyamatban van...'),
	('Elfogadva', 'A szolg�latot sikeresen elfogadt�k!'),
	('Elutas�tva', 'A szolg�latot elutas�tott�k!')