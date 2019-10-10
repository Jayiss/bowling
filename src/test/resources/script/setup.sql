//create tables
drop table if exists games;
create table if not exists games
(
    game_id  int,
    max_turn int,
    max_pin  int,
    constraint games_pk
        primary key (game_id)
);

drop table if exists turns;
create table if not exists turns
(
    turn_id    int,
    game_id    int,
    first_pin  int,
    second_pin int,
    constraint turns_pk
        primary key (turn_id),
    constraint turns_games_game_id_fk
        foreign key (game_id) references games
            on delete cascade
);