package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    Statement st;
    Connection conn;

    // Turn table includes id, firstPin, secondPin and foreign id
    public BowlingTurnDaoImpl(Connection connection) {
        try {
            conn = connection;
            st = conn.createStatement();
//            st.execute("CREATE TABLE turn (\n" +
//                    "  `id` int(11),\n" +
//                    "  `firstPin` int(11),\n" +
//                    "  `secondPin` int(11),\n" +
//                    "  `foreignKey` int(11),\n" +
//                    "  PRIMARY KEY (`id`)\n" +
//                    ")");
//            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    // According to given foreignId(Game ID), get a list consisted of all the turn keys.
    protected List<TurnKey> loadAllKey(int foreignId) {
        return null;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // According to given turn key, return an entity.
    protected BowlingTurnEntity doLoad(TurnKey id) {
        return null;
    }

    @Override
    // After complete loading, build its corresponding domain
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    // remove a game by its id
    public boolean remove(TurnKey key) {
        return false;
    }
}
