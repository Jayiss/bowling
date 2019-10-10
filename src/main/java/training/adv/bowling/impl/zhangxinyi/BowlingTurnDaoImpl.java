package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Statement st;
    private Connection conn;

    // Turn table includes id, firstPin, secondPin and foreign id
    public BowlingTurnDaoImpl(Connection connection) {
        try {
            conn = connection;
            st = conn.createStatement();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    // According to given foreignId(Game ID), get a list consisted of all the turn keys.
    protected List<TurnKey> loadAllKey(int foreignId) {
        Integer lId = null;
        Integer lFirstPin = null;
        Integer lSecondPin = null;
        List<TurnKey> turnKeys = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM turn WHERE foreignKey = '" + foreignId + "'");
            conn.commit();
            while (rs.next()) {
                lId = rs.getInt(1);
                TurnKey key = new BowlingTurnKeyImpl(lId, foreignId);
                turnKeys.add(key);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnKeys;
    }

    @Override
    // Save each turn entity.
    protected void doSave(BowlingTurnEntity entity) {
        try {
            int second;
            if (entity.getSecondPin() == null) {
                second = -1;
            } else {
                second = entity.getSecondPin();
            }
            st.execute("INSERT INTO turn (id, firstPin, secondPin, foreignKey) VALUES ('"
                    + entity.getId().getId() + "', '"
                    + entity.getFirstPin() + "', '" + second + "', '"
                    + entity.getId().getForeignId() + "')");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // According to given turn key, return an entity.
    protected BowlingTurnEntity doLoad(TurnKey id) {
        Integer lFirstPin = null;
        Integer lSecondPin = null;
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM turn WHERE id = '" + id.getId() + "'");
            conn.commit();
            while (rs.next()) {
                lFirstPin = rs.getInt(2);
                lSecondPin = rs.getInt(3);
                if (lSecondPin == -1) {
                    lSecondPin = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BowlingTurnEntity en = new BowlingTurnEntityImpl();
        en.setId(id);
        en.setFirstPin(lFirstPin);
        en.setSecondPin(lSecondPin);
        return en;
    }

    @Override
    // After complete loading, build its corresponding domain
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        BowlingTurn turn = new BowlingTurnImpl(entity.getFirstPin(), entity.getSecondPin());
        turn.getEntity().setId(entity.getId());
        return turn;
    }

    @Override
    // remove a game by its id
    public boolean remove(TurnKey key) {
        try {
            st.execute("DELETE FROM turn WHERE id = '" + key.getId() + "'");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
