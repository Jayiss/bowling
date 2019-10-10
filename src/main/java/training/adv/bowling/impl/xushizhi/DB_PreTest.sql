-- Create Table - Game
create table Game
(
	id_game int auto_increment,
	max_turn int default 10 not null,
	constraint Game_pk
		primary key (id_game)
);

comment on table Game is 'PK - Game ID (Auto Inc)';


-- Create Table - Turn
create table Turn
(
	id_turn int not null,
	id_game int not null,
	firstPin int,
	secondPin int,
	constraint Turn_pk
		primary key (id_turn),
	constraint Turn_GAME_ID_GAME_fk
		foreign key (id_game) references GAME
);

comment on table Turn is 'PK - Turn ID, FK - Game ID';


-- Insert Into Table Game
INSERT INTO GAME (MAX_TURN) VALUES (10);
INSERT INTO GAME (MAX_TURN) VALUES (15);
INSERT INTO GAME (MAX_TURN) VALUES (20);


-- Insert Into Table Turn
INSERT INTO TURN (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (1, 3, 5, 5);
INSERT INTO TURN (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (4, 2, 10, 0);
INSERT INTO TURN (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (10, 1, 3, 7);


-- Display Table Game
SELECT t.*
FROM PUBLIC.GAME t
LIMIT 501


-- Display Table Turn
SELECT t.*
FROM PUBLIC.TURN t
LIMIT 501


--Delete from Table Game
DELETE FROM "PUBLIC"."GAME"
WHERE "ID_GAME" = 1


-- Delete from Table Turn
DELETE FROM "PUBLIC"."TURN"
WHERE "ID_TURN" = 9
