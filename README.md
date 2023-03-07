# Önálló labor Andorid :iphone:

## Projekt rövid leírása :clipboard:

## Adatbázis :floppy_disk:

| Tábla          | Oszlop          | Leírás                                                       |
| -------------- | --------------- | ------------------------------------------------------------ |
| **People**     | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Name            | Felhasználó teljes neve                                      |
|                | Username        | Felhasználónév                                               |
|                | Nickname        | Felhasználó beceneve                                         |
|                | Email           | Felhasználó emailcíme                                        |
|                | Password        | Felhasználó jelszava, titkosítva                             |
|                | ProfilePicture  | Profilkép                                                    |
| **Shifts**     | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | StartTime       | Szolgálat kezdeti időpontja                                  |
|                | EndTime         | Szolgálat végének időpontja                                  |
|                | EarnedMoney     | Szolgálatban megszerzett pénzösszeg (ledolgozott idő és az órabér szorzata) |
|                | PeopleID        | Szolgálatot teljesítő felhasználó                            |
|                | JobID           | Munka azonosítója                                            |
|                | WageID          | Munkabér kategória azonosítója                               |
|                | StatusID        | A szolgálat státuszának azonosítója (pl.: Befejezett, Elutasított) |
| **Wages**      | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Name            | Munkabér megnevezése                                         |
|                | Wage            | Órabér (Ft/óra)                                              |
| **Jobs**       | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Pin             | 6 karakterből álló csatlakozási kód                          |
|                | Title           | Munka címe (pl.: Fishing On Orfű 2023)                       |
|                | Description     | Munka rövid leírása                                          |
|                | PeopleID        | Munkát létrehozott felhasználó azonosítója                   |
| **Dashboard**  | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Title           | Üzenet címe                                                  |
|                | Message         | Üzenet törzse                                                |
|                | CreationTime    | Munka létrehozásának időpontja                               |
|                | JobID           | Annak a munka azonosítója amin az üzenetet létrehozták       |
|                | PeopleID        | Üzenetet létrehozó felhasználó azonosítója                   |
|                | WageID          | Azon bérkategória azonosítója akinek az üzenet szól          |
| **Roles**      | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Title           | Jogosultság címe (pl.: Admin, User, Owner)                   |
|                | CanMakeMessage  | Üzenet létrehozásának joga                                   |
|                | CanManageShifts | Szolgálatok kezelésének joga (pl.: Elfogadás, Elutasítás)    |
|                | CanChangeWages  | Bérkategória megváltoztathatóságának joga                    |
|                | CanAddRole      | Jogosultság adsának joga                                     |
|                | CanLoseRole     | Jogosultság elvesztésének joga                               |
| **PeopleJobs** | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | JobID           | Munka azonosítója                                            |
|                | PeopleID        | Felhasználó azonosítója                                      |
|                | RoleID          | Jogosultság azonosítója                                      |
|                | WageID          | Bérkategória azonosítója                                     |
| **States**     | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Title           | Állapot címe (pl.: Folyamatban, Befejezve, ...)              |
|                | Description     | Állapot leírása, magyarázata                                 |
| **Positions**  | ID              | Automatikusan generált azonosító, elsődleges kulcs.          |
|                | Time            | Létrehozás időpontja                                         |
|                | Longitude       |                                                              |
|                | Latitude        |                                                              |
|                | PeopleID        | Felhasználó azonosítója akihez a pozíció tartozik            |

