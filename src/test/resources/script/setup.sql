create table game
(
id int primary key not null,
score int ,
turn int ,
pins int
);


create table turn
(
id int  not null,
foreignid int not null,
firstpin int null,
secondpin int null
);

insert into game values (1001,300,10,10);
insert into turn values (1,1001,10,0);
insert into turn values (2,1001,10,0);
insert into turn values (3,1001,10,0);
insert into turn values (4,1001,10,0);
insert into turn values (5,1001,10,0);
insert into turn values (6,1001,10,0);
insert into turn values (7,1001,10,0);
insert into turn values (8,1001,10,0);
insert into turn values (9,1001,10,0);
insert into turn values (10,1001,10,0);
insert into turn values (11,1001,10,0);
insert into turn values (12,1001,10,0);