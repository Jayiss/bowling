package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    Connection connection;

    public BowlingTurnDaoImpl(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> allKey = new ArrayList<>();
        try {
            PreparedStatement queryStatement = DBUtil.getConnection().prepareStatement("select * from turns where " +
                    "game_id = ?;");
            queryStatement.setInt(1, foreignId);
            ResultSet rs = queryStatement.executeQuery();
            TurnKey turnKey;
            while (rs.next()) {
                turnKey = new BowlingTurnKeyImpl(rs.getInt("TURN_ID"), rs.getInt("GAME_ID"));
                allKey.add(turnKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allKey;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
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
        BowlingTurnEntity result = null;
        try {
            PreparedStatement queryStatement = DBUtil.getConnection().prepareStatement("select * from turns where " +
                    "turn_id = ?;");
            queryStatement.setInt(1, id.getId());
            ResultSet rs = queryStatement.executeQuery();

            TurnKey turnKey;
            if (rs.next()) {
                turnKey = new BowlingTurnKeyImpl(rs.getInt("TURN_ID"), rs.getInt("GAME_ID"));
                result = new BowlingTurnImpl();
                result.setId(turnKey);
                result.setFirstPin(rs.getInt("FIRST_PIN"));
                int secondPin = rs.getInt("SECOND_PIN");
                result.setSecondPin(secondPin == -1 ? null : secondPin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        BowlingTurnImpl domain = new BowlingTurnImpl();
        domain.setId(entity.getId());
        domain.setFirstPin(entity.getFirstPin());
        domain.setSecondPin(entity.getSecondPin());
        return domain;
    }

    @Override
    public boolean remove(TurnKey key) {
        return false;
    }
}
