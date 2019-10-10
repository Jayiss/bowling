drop table if exists bowling_game;
drop table if exists turn;
create table bowling_game(id INT PRIMARY KEY,
                          total_scores INT,
                          max_turn INT,
                          max_pins INT);
create table turn(id INT ,
                  foreign_id  INT ,
                  first_score INT,
                  second_score INT,
                  PRIMARY key (id,foreign_id),
                  FOREIGN KEY (foreign_id) REFERENCES bowling_game(id) on delete cascade on update cascade);