package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.net.IDN;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame,Integer> implements BowlingGameDao {
    @Override
    protected void doSave(BowlingGameEntity entity) {
        if(entity!=null  ){
            Connection connection = DBUtil.getConnection();
            String sql = "INSERT INTO BOWLINGGAME VALUES(?,?)";
            try {
                PreparedStatement psmt = connection.prepareStatement(sql);
                if(entity.getId()!=null)
                    psmt.setInt(1,entity.getId());
                else
                    psmt.setInt(1,Sequence.ID);
                //Sequence.ID++;
                psmt.setInt(2,entity.getMaxTurn());
                psmt.executeUpdate();
                entity.setId(Sequence.ID);
                connection.commit();
                psmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Sequence.ID++;
        }

    }

    public void save(BowlingGame bowlingGame){
        doSave(bowlingGame.getEntity());
    }

    @Override
    public BowlingGameEntity doLoad(Integer id) {
        if(id !=null){
            BowlingGameEntityImpl gameEntity = new BowlingGameEntityImpl();
            Connection connection = DBUtil.getConnection();
            String sql = "SELECT * FROM BOWLINGGAME WHERE ID = ?";
            try {
                PreparedStatement psmt = connection.prepareStatement(sql);
                psmt.setInt(1,id);
                ResultSet rs = psmt.executeQuery();
                while(rs.next()){
                    gameEntity.setId(rs.getInt("ID"));
                    gameEntity.setMaxTurn(rs.getInt("MAXTURN"));
                }
                return gameEntity;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        else
            return null;
    }
    public BowlingGameImpl load(Integer id){
        BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl();
        BowlingGameImpl bowlingGame = new BowlingGameImpl(new BowlingRuleImpl());
        BowlingGameDaoImpl bowlingGameDao = new BowlingGameDaoImpl();
        bowlingGameEntity = bowlingGameDao.doLoad(id);

        bowlingGame.setId(bowlingGameEntity.getId());

        return bowlingGame;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        if(entity!=null){
            BowlingRuleImpl bowlingRule = new BowlingRuleImpl();
            bowlingRule.setMaxTurn(entity.getMaxTurn());
            BowlingGameImpl bowlingGame = new BowlingGameImpl(bowlingRule);

            bowlingGame.setExistingTurns(bowlingRule.getExistingTurns());
            bowlingGame.setScores(bowlingRule.calcScores(bowlingRule.getExistingTurns()));

            return bowlingGame;
        }
        else
            return null;

    }

    @Override
    public boolean remove(Integer key) {
        if(key !=null){
            Connection connection = DBUtil.getConnection();
            String sql = "DELETE FROM BOWLINGGAME WHERE ID = ?";
            try {
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setInt(1, key);
                pstm.executeUpdate();

                sql = "DELETE FROM BOWLINGTURN WHERE BOWLINGGAMEID = ?";
                pstm = connection.prepareStatement(sql);
                pstm.setInt(1, key);
                pstm.executeUpdate();

                connection.close();
                pstm.close();
                connection.close();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        else
            return false;
    }
}
