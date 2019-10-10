package training.adv.bowling.impl;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.caoyu.BowlingGameDaoImpl;
import training.adv.bowling.impl.caoyu.BowlingTurnDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BowlingServiceImpl implements BowlingService {
    //TODO: implement DBUtil
    private Connection connection = DBUtil.getConnection();

    private BowlingGameDao gameDao = new BowlingGameDaoImpl(connection);
    private BowlingTurnDao turnDao = new BowlingTurnDaoImpl(connection);

    BowlingServiceImpl() {
        String sql;
        try {
            Statement stmt = connection.createStatement();
            sql = "\n" +
                    "drop table if exists games;\n" +
                    "create table if not exists games\n" +
                    "(\n" +
                    "    game_id  int,\n" +
                    "    max_turn int,\n" +
                    "    max_pin  int,\n" +
                    "    constraint games_pk\n" +
                    "        primary key (game_id)\n" +
                    ");\n" +
                    "\n" +
                    "drop table if exists turns;\n" +
                    "create table if not exists turns\n" +
                    "(\n" +
                    "    turn_id    int,\n" +
                    "    game_id    int,\n" +
                    "    first_pin  int,\n" +
                    "    second_pin int,\n" +
                    "    constraint turns_pk\n" +
                    "        primary key (turn_id),\n" +
                    "    constraint turns_GAMES_GAME_ID_fk\n" +
                    "        foreign key (game_id) references GAMES\n" +
                    "            on delete cascade\n" +
                    ");";
            int result = stmt.executeUpdate(sql);
            commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(BowlingGame game) {
        gameDao.save(game);
        for (BowlingTurn turn : game.getTurns()) {
            turnDao.save(turn);
        }
        commit();
    }

    @Override
    public BowlingGame load(Integer id) {
        BowlingGame game = gameDao.load(id);
        List<BowlingTurnEntity> turns = turnDao.batchLoad(id);
        game.getEntity().setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
        return game;
    }

    @Override
    public void remove(Integer id) {
        gameDao.remove(id);
        turnDao.batchRemove(id);
        commit();
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
