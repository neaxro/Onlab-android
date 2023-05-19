SELECT 'Adatbázis kiválasztása (SecurityApp)...' AS 'Feladatok: 1/2';
USE SecurityApp;

SELECT 'People táblához adatok beillesztése...' AS 'Feladatok: 2/2';
INSERT INTO People(`Name`, `Username`, `Nickname`, `Email`, `Password`)
VALUES
    ("Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com", "Asdasd11"),
    ("Szekeres Márk", "kuks", "Kuks", "szekeres.mark@gmail.com", "Asdasd11");