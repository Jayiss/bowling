CREATE TABLE game (
  id int(11),
  maxTurn int(11) NOT NULL DEFAULT '10',
  PRIMARY KEY (id)
)

CREATE TABLE turn (
  id int(11),
  firstPin int(11),
  secondPin int(11),
  foreignKey int(11),
  PRIMARY KEY (id)
)

INSERT INTO game (id, maxTurn) VALUES ('', '');

INSERT INTO turn (firstPin, secondPin, foreignKey) VALUES ('', '', '')

SELECT id FROM game WHERE id = ''

DELETE FROM game;
DELETE FROM turn

DROP TABLE game;
DROP TABLE turn;