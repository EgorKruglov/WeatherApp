use WeatherAppDB;

insert into tblUsers values
(1, "Daniel", "Black", 12345678909, "7dan7iel7@gmail.com", "passw0rd", 1, "2023-05-31"),
(2, "John", "Brown", 21345678909, "@gmail.com", "I234", 0, "2023-05-31"),
(3, "Jack", "White", 42098768909, "@gmail.com", "pa$$w0rd", 0, "2023-05-31"),
(4, "Joshua", "Pink", 22345678909, "@gmail.com", "YHWH", 1, "2023-05-31");

insert into tblAdminCodes values
(1, 1, "special_code"),
(2, 4, "super special code");

insert into tblCustomTriggers values
(1, -10, 30, 70, 120, 2.0, 5.0),
(2, -5, 17, 70, 120, 2.0, 5.0);

insert into tblDefaultTriggers values
(1, -10, 30, 70, 120, 2.0, 5.0),
(2, -5, 17, 70, 120, 2.0, 5.0);

insert into tblCustomConditions values
(1, "continuous-heavy-rain", 1),
(2, "thunderstorm-with-rain", 1),
(3, "cloudy", 2);

insert into tblDefaultConditions values
(1, "continuous-heavy-rain", 1),
(2, "thunderstorm-with-rain", 1),
(3, "cloudy", 2);

insert into tblLocations values
(1, 55.75396, 37.620393, "Moscow", 1, 1),
(2, 59.531112, 50.951581, "Nagorsky", 2, 2);

insert into tblLocationUserRelations values
(1, 1, 1),
(2, 1, 3),
(3, 2, 4);

insert into tblReplyWeather values
(1, 11, 88, "overcast", 3.7);

insert into tblAutoRequestHistory values
(1, 1, "2023-05-31 10:13:09", 1, "2023-05-31 10:13:11");

