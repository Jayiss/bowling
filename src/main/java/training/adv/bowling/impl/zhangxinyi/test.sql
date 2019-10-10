CREATE TABLE game (
  id int(11) NOT NULL AUTO_INCREMENT,
  maxTurn int(11) NOT NULL DEFAULT '10',
  PRIMARY KEY (id)
)

CREATE TABLE turn (
  id int(11) NOT NULL AUTO_INCREMENT,
  firstPin int(11),
  secondPin int(11),
  foreignKey int(11),
  PRIMARY KEY (id)
)

INSERT INTO game (maxTurn) VALUES ('');

DELETE FROM game WHERE id = ''

INSERT INTO turn (firstPin, secondPin, foreignKey) VALUES ('', '', '')

SELECT id IN game WHERE id = ''