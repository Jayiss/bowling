package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

            //game update
//            PreparedStatement updateBowlingGameStatement = connection.prepareStatement("UPDATE PUBLIC.GAMES SET " +
//                    "MAX_TURN = ?, MAX_PIN = ? WHERE GAME_ID = ?;");
//            updateBowlingGameStatement.setInt(1, entity.getMaxTurn());
//            updateBowlingGameStatement.setInt(2, entity.getMaxPin());
//            updateBowlingGameStatement.setInt(3, entity.getId());
//            int result = updateBowlingGameStatement.executeUpdate();

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

        return;
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return null;
    }

    @Override
    public boolean remove(Integer key) {
        return false;
    }
}
