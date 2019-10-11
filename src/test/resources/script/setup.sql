CREATE TABLE games(
  id INT NOT NULL,
  PRIMARY KEY (id));

  CREATE TABLE turns(
  id INT NOT NULL,
  gameId INT NOT NULL ,
  firstTurn INT ,
  secondTurn INT,
  PRIMARY KEY (id,gameId),
  FOREIGN KEY (gameId) REFERENCES games(id));

  INSERT INTO games (id) VALUES (1001);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (1,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (2,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (3,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (4,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (5,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (6,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (7,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (8,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (9,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (10,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (11,1001,10,0);
  INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES (12,1001,10,0);


