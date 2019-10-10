create table game_table(game_id int primary key not null,maxTurn int not null);
create table turns_table(id int primary key auto_increment,turn_id int not null,firstpin int,secondpin int,game_id int not null, foreign key (game_id) references game_table(game_id));

insert into game_table(game_id,maxTurn) values(1001,10);
insert into turns_table(turn_id,firstpin,secondpin,game_id)values(0,10,0,1001);
insert into turns_table(turn_id,firstpin,secondpin,game_id)values(0,10,0,1001);
insert into turns_table(turn_id,firstpin,secondpin,game_id)values(0,10,0,1001);
insert into turns_table(turn_id,firstpin,secondpin,game_id)values(0,10,0,1001);