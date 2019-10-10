package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BowlingGameDaoImpl extends AbstractDao<GameEntity, BowlingGame, Integer> implements BowlingGameDao {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(GameEntity entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"PUBLIC\".\"GAMES\" " +
                    "(\"GAME_ID\", \"MAX_TURN\") VALUES (?, ?)");
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getMaxTurn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return;
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
