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
            sql = "drop table games;\n" +
                    "create table if not exists games\n" +
                    "(\n" +
                    "\tgame_id int,\n" +
                    "\tmax_turn int,\n" +
                    "\tconstraint games_pk\n" +
                    "\t\tprimary key (game_id)\n" +
                    ");\n" +
                    "\n" +
                    "drop table turns;\n" +
                    "create table if not exists turns\n" +
                    "(\n" +
                    "\tturn_id int,\n" +
                    "\tgame_id int,\n" +
                    "\tfirst_pin int,\n" +
                    "\tsecond_pin int,\n" +
                    "\tconstraint turns_pk\n" +
                    "\t\tprimary key (turn_id),\n" +
                    "\tconstraint turns_GAMES_GAME_ID_fk\n" +
                    "\t\tforeign key (turn_id) references GAMES\n" +
                    "\t\t\ton delete cascade\n" +
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
