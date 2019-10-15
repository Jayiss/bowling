package training.adv.bowling.impl;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.xushizhi.BowlingGameDaoImpl;
import training.adv.bowling.impl.xushizhi.BowlingTurnDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BowlingServiceImpl implements BowlingService {
    //TODO: implement DBUtil
    private Connection connection = DBUtil.getConnection();

    private BowlingGameDao gameDao = new BowlingGameDaoImpl(connection);
    private BowlingTurnDao turnDao = new BowlingTurnDaoImpl(connection);

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
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
