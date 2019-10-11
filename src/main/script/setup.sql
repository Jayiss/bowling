create table game
(
id int primary key not null,
score int ,
turn int ,
pins int
)


create table turn
(
id int primary key not null,
foreignid int ID GAMEGRADE not null,
firstpin int null,
secondpin int null
)