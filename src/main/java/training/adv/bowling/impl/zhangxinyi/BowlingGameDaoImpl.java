package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BowlingGameDaoImpl extends AbstractDao<GameEntity, BowlingGame, Integer> implements BowlingGameDao {
    Statement st;
    Connection conn;

    // Initialize DB and create the table.
    // Game table includes id and max turn.
    public BowlingGameDaoImpl(Connection connection) {
        try {
            conn = connection;
            conn.setAutoCommit(false);
            st = conn.createStatement();
//            st.execute("CREATE TABLE game (\n" +
//                    "  id int(11) NOT NULL AUTO_INCREMENT,\n" +
//                    "  maxTurn int(11) NOT NULL DEFAULT '10',\n" +
//                    "  PRIMARY KEY (id)\n" +
//                    ")");
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    // Use H2 DB to save the game data
    // Auto allocate id
    // Need to call Turn Dao's doSave
    protected void doSave(GameEntity entity) {
        try {
            st.execute("INSERT INTO game (maxTurn) VALUES ('" +
                    new BowlingRuleImpl().getMaxTurn() + "');");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TurnEntity[] turnEntities = entity.getTurnEntities();
        BowlingTurnDaoImpl turnDao = new BowlingTurnDaoImpl(conn);
        for (TurnEntity en : turnEntities) {
            turnDao.doSave((BowlingTurnEntity) en);
        }
    }

    @Override
    // load the BowlingGameEntity according to its id
    protected GameEntity doLoad(Integer id) {
        return null;
    }

    @Override
    // After comlete loading, build its corresponding domain
    protected BowlingGame doBuildDomain(GameEntity entity) {
        return null;
    }

    @Override
    // remove a game by its id
    public boolean remove(Integer key) {
        try {
            st.execute("DELETE FROM game WHERE id = '" + key + "'");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
