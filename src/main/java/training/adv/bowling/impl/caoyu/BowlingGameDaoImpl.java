package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        try {
            //game deleting DELETE FROM PUBLIC.GAMES WHERE GAME_ID = 33;
            PreparedStatement deleteExistingGame = connection.prepareStatement("DELETE FROM PUBLIC.GAMES WHERE " +
                    "GAME_ID = ?;");
            deleteExistingGame.setInt(1, entity.getId());
            deleteExistingGame.executeUpdate();


            //game insertion
            PreparedStatement insertBowlingGameStatement = connection.prepareStatement("INSERT INTO \"PUBLIC\"" +
                    ".\"GAMES\" " +
                    "(\"GAME_ID\", \"MAX_TURN\", \"MAX_PIN\") VALUES (?, ?,?)");
            insertBowlingGameStatement.setInt(1, entity.getId());
            insertBowlingGameStatement.setInt(2, entity.getMaxTurn());
            insertBowlingGameStatement.setInt(3, entity.getMaxPin());
            insertBowlingGameStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        BowlingGameEntity result = null;
        try {
            var queryStatement = DBUtil.getConnection().prepareStatement("select * from games where " +
                    "game_id = ?;");
            queryStatement.setInt(1, id);
            ResultSet rs = queryStatement.executeQuery();

            BowlingRule rule;
            if (rs.next()) {
                rule = new BowlingRuleImpl(rs.getInt("MAX_TURN"), rs.getInt("MAX_PIN"));
                result = new BowlingGameImpl(rule, rs.getInt("GAME_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingRuleImpl rule = new BowlingRuleImpl(entity.getMaxTurn(), entity.getMaxPin());
        BowlingGameImpl domain = new BowlingGameImpl(rule);
        domain.setId(entity.getId());
        return domain;
    }

    @Override
    public boolean remove(Integer key) {
        try {
            PreparedStatement deleteExistingGame = connection.prepareStatement("DELETE FROM PUBLIC.GAMES WHERE " +
                    "GAME_ID = ?;");
            deleteExistingGame.setInt(1, key);
            deleteExistingGame.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
