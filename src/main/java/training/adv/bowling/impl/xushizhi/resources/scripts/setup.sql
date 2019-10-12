-- Create Table - Game
create table Game
(
    id_game int
    auto_increment,
	max_turn int default 10 not null,
	constraint Game_pk
		primary key
    (id_game)
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
	primary key (id_turn, id_game),
    constraint Turn_GAME_ID_GAME_fk
	foreign key (id_game) references GAME
);

comment on table Turn is 'PK - Turn ID, FK - Game ID';


-- Insert Data Into Table - Game
INSERT INTO Game (ID_GAME, MAX_TURN) VALUES (1001, 10);


-- Insert Data Into Table - Turn
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (1, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (2, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (3, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (4, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (5, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (6, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (7, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (8, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (9, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (10, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (11, 1001, 10, -1);
INSERT INTO Turn (ID_TURN, ID_GAME, firstPin, secondPin) VALUES (12, 1001, 10, -1);
