package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.*;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {

    private Connection connection;
    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void save(BowlingGame domain) {
        doSave((BowlingGameImpl)domain);
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {

    }


    protected void doSave(BowlingGameImpl entity) {
        try {
            String sql = "insert into  game (game_id,total_score,max_turn,max_pin) values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getTotalScore());
            preparedStatement.setInt(3, entity.getMaxTurn());
            preparedStatement.setInt(4, entity.getMaxPin());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public BowlingGame load(Integer id) {
        try(Connection connection = DBUtil.getConnection()){
            String sql = "select * from game where game_id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet set= preparedStatement.executeQuery();
            BowlingGameImpl bowlingGameImpl = new BowlingGameImpl(new BowlingRuleImpl());
            if (set.next()){
                bowlingGameImpl.setGameId(set.getInt(1));
                bowlingGameImpl.setTotalScore(set.getInt(2));
                bowlingGameImpl.setMaxTurn(set.getInt(3));
                bowlingGameImpl.setMaxPin(set.getInt(4));
                return (BowlingGame) bowlingGameImpl;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        /*try(Connection connection = DBUtil.getConnection()){
            String sql = "select * from game where game_id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet set= preparedStatement.executeQuery();
            BowlingGameEntityImpl bowlingGameEntity = new BowlingGameEntityImpl();
            if (set.next()){
                bowlingGameEntity.setGameId(set.getInt(1));
                bowlingGameEntity.setTotalScore(set.getInt(2));
                bowlingGameEntity.setMaxTurn(set.getInt(3));
                bowlingGameEntity.setMaxPin(set.getInt(4));
                return bowlingGameEntity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        try {
            String sql = "delete from  game where game_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
