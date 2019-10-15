package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.*;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {

    private PreparedStatement pstmt = null;
    private Connection conn = null;
    private ResultSet rs = null;

    // H2 Database GAME table initialization - id_game(PK), MAX_TURN
    public BowlingGameDaoImpl(Connection connection) {
        try {
            conn = connection;
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // Close ResultSet
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        // Close PreparedStatement
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        // Close Connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    // Save bowling game entity to H2 DB
    protected void doSave(BowlingGameEntity entity) {
        int maxTurn = new BowlingRuleImpl().getMaxTurn(), id_game = -1;

        String sql = "Insert Into GAME (MAX_TURN) Values (?)";
        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, maxTurn);
            pstmt.executeUpdate();
            conn.commit();

            // Get insert action generated auto-increment PK - id_game
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id_game = rs.getInt(1);
                entity.setId(id_game);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }

        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();
        for (BowlingTurnEntity turnEntity : turnEntities) {
            BowlingTurnEntityImpl.id_turn++;
            turnEntity.setId(new TurnKeyImpl(BowlingTurnEntityImpl.id_turn, id_game));
        }
    }

    @Override
    // Get the bowling game entity data from H2 DB (by Game ID)
    protected BowlingGameEntity doLoad(Integer id) {
        String sql = "Select * From GAME Where id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }
        BowlingRule bowlingRule = new BowlingRuleImpl();
        BowlingGameEntity gameEntity = new BowlingGameImpl(bowlingRule).getEntity();
        gameEntity.setId(id);
        return gameEntity;
    }

    @Override
    // Build current game's domain after loading process has completed
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame bowlingGame = new BowlingGameImpl(new BowlingRuleImpl());
        bowlingGame.getEntity().setId(entity.getId());
        return bowlingGame;
    }

    @Override
    // Remove designated game from H2 DB
    public boolean remove(Integer key) {
        String sql_1 = "Delete From TURN Where id_game = ?";
        String sql_2 = "Delete From GAME Where id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql_1);
            pstmt.setInt(1, key);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(sql_2);
            pstmt.setInt(1, key);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, null);
        }
        return true;
    }
}
