package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    Connection connection;

    public BowlingTurnDaoImpl(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        return null;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {//INSERT into PUBLIC.TURNS (turn_id, game_id, first_pin, second_pin) values (1, 2, 3, 4);
        //game insertion
        try (PreparedStatement insertBowlingGameStatement = connection.prepareStatement("INSERT into PUBLIC.TURNS " +
                "(turn_id, game_id, first_pin, second_pin) values (?, ?, ?, ?);")) {
            insertBowlingGameStatement.setInt(1, entity.getId().getId());
            insertBowlingGameStatement.setInt(2, entity.getId().getForeignId());
            insertBowlingGameStatement.setInt(3, entity.getFirstPin());
            if (null != entity.getSecondPin())
                insertBowlingGameStatement.setInt(4, entity.getSecondPin());
            else
                insertBowlingGameStatement.setInt(4, -1);
            insertBowlingGameStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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
