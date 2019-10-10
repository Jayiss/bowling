package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {
    Statement st;
    Connection conn;

    // Initialize DB and create the table.
    // Game table includes id and max turn.
    public BowlingGameDaoImpl(Connection connection) {
        try {
            conn = connection;
            st = conn.createStatement();
//            st.execute("CREATE TABLE game (\n" +
//                    "  id int(11),\n" +
//                    "  maxTurn int(11) NOT NULL DEFAULT '10',\n" +
//                    "  PRIMARY KEY (id)\n" +
//                    ")");
//            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    // Use H2 DB to save the game data
    // Auto allocate id
    // Need to call Turn Dao's doSave
    protected void doSave(BowlingGameEntity entity) {
        try {
            BowlingGameEntityImpl.uniqueId += 1;
            st.execute("INSERT INTO game (id, maxTurn) VALUES ('" + BowlingGameEntityImpl.uniqueId + "', '" +
                    new BowlingRuleImpl().getMaxTurn() + "');");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        entity.setId(BowlingGameEntityImpl.uniqueId);
        TurnEntity[] turnEntities = entity.getTurnEntities();
        BowlingTurnDaoImpl turnDao = new BowlingTurnDaoImpl(conn);
        for (TurnEntity en : turnEntities) {
            BowlingTurnEntityImpl.uniqueId += 1;
            ((BowlingTurnEntityImpl) en).setId(new BowlingTurnKeyImpl(BowlingTurnEntityImpl.uniqueId, BowlingGameEntityImpl.uniqueId));
        }
    }

    @Override
    // load the BowlingGameEntity according to its id
    protected BowlingGameEntity doLoad(Integer id) {
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM game WHERE id = '" + id + "'");

            conn.commit();
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                Integer lTurnMax = rs.getInt(2);
            }
            System.out.println("Now Empty");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        BowlingRule rule = new BowlingRuleImpl();
        BowlingGameEntity en = new BowlingGameImpl(rule).getEntity();
        en.setId(id);
        return en;
    }

    @Override
    // After complete loading, build its corresponding domain
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame game = new BowlingGameImpl(new BowlingRuleImpl());
        game.getEntity().setId(entity.getId());
        return game;
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
