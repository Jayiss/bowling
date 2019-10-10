package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Connection connection;

    public BowlingTurnDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        try {
            String selectSql =
                    "select tid from TURNS where gid =?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setInt(1, foreignId);

            ResultSet resultSet = stmt.executeQuery();
            List<TurnKey> turnKeys = new ArrayList<>();
            while (resultSet.next()) {
                Integer turnId = resultSet.getInt(1);
                turnKeys.add(new TurnKeyImpl(turnId, foreignId));
            }
            return turnKeys;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        try {
            String insertSql =
                    "insert into TURNS(FIRST_PIN,SECOND_PIN,GID) values(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, entity.getFirstPin());
            if (entity.getSecondPin() != null)
                stmt.setInt(2, entity.getSecondPin());
            else
                stmt.setNull(2, Types.NULL);
            stmt.setInt(3, entity.getId().getForeignId());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            Integer newKey = null;
            if (resultSet.next()) {
                newKey = (Integer) resultSet.getObject(1);
            }
            TurnKey key = new TurnKeyImpl(newKey, entity.getId().getForeignId());
            entity.setId(key);
//            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        try {
            String selectSql =
                    "select * from TURNS where tid =?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setInt(1, id.getId());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Integer turnId = resultSet.getInt(1);
                Integer turnFirstPin = resultSet.getInt(2);

                Integer turnSecondPin = resultSet.getInt(3);
                if (resultSet.wasNull()) {
                    turnSecondPin = null;
                }
                Integer turnGid = resultSet.getInt(4);

                BowlingTurn bowlingTurn = new BowlingTurnImpl(turnFirstPin, turnSecondPin);
                bowlingTurn.getEntity().setId(new TurnKeyImpl(turnId, turnGid));
                return bowlingTurn.getEntity();
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return (BowlingTurnImpl) entity;
    }

    @Override
    public boolean remove(TurnKey key) {
        return false;
    }
}
