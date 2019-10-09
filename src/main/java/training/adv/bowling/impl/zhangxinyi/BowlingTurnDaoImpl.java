package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {

    @Override
    // According to given foreignID, get a list consisted of all the turn keys.
    protected List<TurnKey> loadAllKey(int foreignId) {
        return null;
    }

    @Override
    // Notice BowlingServiceImpl includes "save" method.
    protected void doSave(BowlingTurnEntity entity) {

    }

    @Override
    // According to given turn key, return an entity.
    protected BowlingTurnEntity doLoad(TurnKey id) {
        return null;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    public boolean remove(TurnKey key) {
        return false;
    }
}
