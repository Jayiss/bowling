package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.io.Serializable;
import java.sql.Connection;

public class BowlingGameDaoImpl extends AbstractDao<GameEntity, BowlingGame, Integer> implements BowlingGameDao {
    
    public BowlingGameDaoImpl(Connection connection) {
        super();
    }

    @Override
    protected void doSave(GameEntity entity) {

    }

    @Override
    protected GameEntity doLoad(Integer id) {
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(GameEntity entity) {
        return null;
    }

    @Override
    public boolean remove(Integer key) {
        return false;
    }
}
