package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;

import java.net.ConnectException;
import java.sql.Connection;

public class BowlingGameDaoImpl implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection conn)
    {
         connection=conn;
    }
    @Override
    public void save(BowlingGame domain) {
        return ;
    }

    @Override
    public BowlingGame load(Integer id) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }
}
