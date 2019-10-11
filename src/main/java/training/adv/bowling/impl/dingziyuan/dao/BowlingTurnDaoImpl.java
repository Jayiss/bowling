package training.adv.bowling.impl.dingziyuan.dao;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.dingziyuan.BowlingTurnImpl;
import training.adv.bowling.impl.dingziyuan.TurnKeyImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Connection connection;

    public BowlingTurnDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(String foreignId) {
        try {
            String selectSql =
                    "select tid from TURNS where gid =?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, foreignId);

            ResultSet resultSet = stmt.executeQuery();
            List<TurnKey> turnKeys = new ArrayList<>();
            while (resultSet.next()) {
                String turnId = resultSet.getString(1);
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
                    "insert into TURNS(TID, FIRST_PIN, SECOND_PIN, GID) values(?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, entity.getId().getId());
            stmt.setInt(2, entity.getFirstPin());
            if (entity.getSecondPin() != null)
                stmt.setInt(3, entity.getSecondPin());
            else
                stmt.setNull(3, Types.NULL);
            stmt.setString(4, entity.getId().getForeignId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        try {
            String selectSql =
                    "SELECT * FROM TURNS WHERE TID =? and GID=?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, id.getId());
            stmt.setString(2, id.getForeignId());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                //Build a BowlingTurnEntity
                String turnId = resultSet.getString(1);
                Integer turnFirstPin = resultSet.getInt(2);
                Integer turnSecondPin = resultSet.getInt(3);
                if (resultSet.wasNull()) {
                    turnSecondPin = null;
                }
                String gameId = resultSet.getString(4);

                BowlingTurn bowlingTurn = new BowlingTurnImpl(turnFirstPin, turnSecondPin);
                bowlingTurn.getEntity().setId(new TurnKeyImpl(turnId, gameId));
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
        try {
            String removeTurnSql =
                    "DELETE FROM TURNS WHERE TID = ?";
            PreparedStatement stmt = connection.prepareStatement(removeTurnSql);
            stmt.setString(1, key.getId());
            stmt.executeUpdate();
            return true;
//            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
