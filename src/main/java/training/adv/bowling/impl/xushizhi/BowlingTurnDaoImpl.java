package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {

    private PreparedStatement pstmt = null;
    private Connection conn = null;
    private ResultSet rs = null;

    // H2 DB TURN table initialization - id_turn(PK), id_game(FK), MAX_TURN
    public BowlingTurnDaoImpl(Connection connection) {
        try {
            conn = connection;
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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
    // Get all turn keys (id_turn) of designated game by given FK (id_game / foreignId)
    protected List<TurnKey> loadAllKey(int foreignId) {
        Integer id_turn = null;
        List<TurnKey> turnKeys = new ArrayList<>();

        String sql = "Select * From TURN Where id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, foreignId);
            rs = pstmt.executeQuery();
            conn.commit();

            // Get turn key -> Generate TurnKey -> Add to List<TurnKey> turnKeys
            while (rs.next()) {
                id_turn = rs.getInt(1);
                TurnKey key = new TurnKeyImpl(id_turn, foreignId);  // id_turn, id_game
                turnKeys.add(key);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }
        return turnKeys;
    }

    @Override
    // Save current turn's entity to H2 DB
    protected void doSave(BowlingTurnEntity entity) {
        int secondPin = -1;
        if (entity.getSecondPin() != null) {
            secondPin = entity.getSecondPin();
        }

        String sql = "Insert Into TURN (id_turn, id_game, firstPin, secondPin) Values (?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, entity.getId().getId());  // id_turn
            System.out.println("id_turn: " + entity.getId().getId());  //TODO
            pstmt.setInt(2, entity.getId().getForeignId());  // id_game
            System.out.println("id_game: " + entity.getId().getForeignId());
            pstmt.setInt(3, entity.getFirstPin());  // firstPin
            System.out.println("firstPin: " + entity.getFirstPin());
            pstmt.setInt(4, secondPin);  // secondPin
            System.out.println("secondPin: " + secondPin);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, null);
        }
    }

    @Override
    // Load designated turn entity from DB by given id_TurnKey
    protected BowlingTurnEntity doLoad(TurnKey id) {
        Integer firstPin = null, secondPin = null;

        String sql = "Select * From TURN Where id_turn = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id.getId());  // Get current TurnKey's ID (id_turn)
            rs = pstmt.executeQuery();
            conn.commit();

            // Get current turn 1st & 2nd Pin
            while (rs.next()) {
                firstPin = rs.getInt(3);
                if (rs.getInt(4) != -1) {
                    secondPin = rs.getInt(4);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }

        BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
        turnEntity.setId(id);  // Turn Key
        turnEntity.setFirstPin(firstPin);
        turnEntity.setSecondPin(secondPin);
        return turnEntity;
    }

    @Override
    // Build current turn's domain after loading process has completed
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        BowlingTurn bowlingTurn = new BowlingTurnImpl(entity.getFirstPin(), entity.getSecondPin());
        bowlingTurn.getEntity().setId(entity.getId());
        return bowlingTurn;
    }

    @Override
    // Remove designated turn from H2 DB
    public boolean remove(TurnKey key) {
        String sql = "Delete From TURN Where id_turn = ? And id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, key.getId());  // TurnKey's ID (id_turn)
            pstmt.setInt(2, key.getForeignId());  // TurnKey's FK (id_game)
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
