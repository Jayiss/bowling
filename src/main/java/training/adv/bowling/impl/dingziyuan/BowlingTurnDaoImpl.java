package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;

import java.sql.Connection;
import java.util.List;

public class BowlingTurnDaoImpl implements BowlingTurnDao {

    private Connection connection;

    public BowlingTurnDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(BowlingTurn domain) {

    }

    @Override
    public List<BowlingTurnEntity> batchLoad(int foreignId) {
        return null;
    }

    @Override
    public void batchRemove(int foreignId) {

    }
}
