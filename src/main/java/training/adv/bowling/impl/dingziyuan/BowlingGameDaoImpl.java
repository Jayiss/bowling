package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.io.Serializable;
import java.sql.*;
import java.util.UUID;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity,BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        try {
            BowlingGame game = doBuildDomain(entity);
            String insertSql =
                    "insert into GAMES(TOTAL_SCORE,MAX_TURN) values(?,?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            Integer a = game.getTotalScore();
            stmt.setInt(1, game.getTotalScore());
            stmt.setInt(2, entity.getMaxTurn());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            Integer newKey = null;
            if (resultSet.next()) {
                newKey = (Integer) resultSet.getObject(1);
            }
            entity.setId(newKey);
            for(BowlingTurnEntity turnEntity : entity.getTurnEntities())
            {
                turnEntity.setId(new TurnKeyImpl(0,newKey));
            }
//            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        try {
            String selectSql =
                    "select * from GAMES where gid =?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Integer gameId = resultSet.getInt(1);
                Integer gameTotalScore = resultSet.getInt(2);
                Integer gameMAX_TURN = resultSet.getInt(3);

                BowlingGame bowlingGame = new BowlingGameImpl(new BowlingRuleImpl(gameMAX_TURN,10));
                bowlingGame.getEntity().setId(gameId);
              return bowlingGame.getEntity();
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame game = (BowlingGameImpl)entity;

        return game;
    }

    @Override
    public boolean remove(Integer key) {
        return false;
    }
}
