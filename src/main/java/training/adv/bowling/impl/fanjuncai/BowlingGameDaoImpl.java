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

public class BowlingGameDaoImpl extends AbstractDao<GameEntity, BowlingGame,Integer> implements BowlingGameDao {
    @Override
    protected void doSave(GameEntity entity) {
        if(entity!=null  ){
            Connection connection = DBUtil.getConnection();
            String sql = "INSERT INTO BOWLINGGAME VALUES(?,?)";
            try {
                PreparedStatement psmt = connection.prepareStatement(sql);
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

            BowlingTurnDaoImpl bowlingTurnDao = new BowlingTurnDaoImpl();
            Integer countturn = 1;
            TurnKeyImpl turnKey = new TurnKeyImpl();
            turnKey.setForeignId(Sequence.ID);
            for(TurnEntity entity1:entity.getTurnEntities()){
                turnKey.setId(countturn);
                entity1.setId(turnKey);
                bowlingTurnDao.doSave((BowlingTurnEntity) entity1);
                countturn++;
            }
            Sequence.ID++;
        }

    }

    @Override
    public GameEntity doLoad(Integer id) {
        if(id !=null){

            Integer count = 1;
            GameEntityImpl gameEntity = new GameEntityImpl();
            BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
            ArrayList<TurnEntity> turnEntities = new ArrayList<>();
            Connection connection = DBUtil.getConnection();
            String sql = "SELECT * FROM BOWLINGGAME WHERE ID = ?";
            try {
                PreparedStatement psmt = connection.prepareStatement(sql);
                psmt.setInt(1,id);
                ResultSet rs = psmt.executeQuery();
                while(!rs.next()){
                    gameEntity.setId(rs.getInt("ID"));
                    gameEntity.setMaxTurn(rs.getInt("MAXTURN"));
                    TurnKeyImpl turnKey = new TurnKeyImpl();
                    turnKey.setId(count++);
                    turnKey.setForeignId(rs.getInt("ID"));
                    BowlingTurnDaoImpl bowlingTurnDao = new BowlingTurnDaoImpl();
                    bowlingTurnEntity = bowlingTurnDao.doLoad(turnKey);
                    while (bowlingTurnEntity!=null){
                        turnEntities.add(bowlingTurnEntity);
                        turnKey.setId(count++);
                        turnKey.setForeignId(rs.getInt("ID"));
                        bowlingTurnDao = new BowlingTurnDaoImpl();
                        bowlingTurnEntity = bowlingTurnDao.doLoad(turnKey);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return gameEntity;
        }
        else
            return null;
    }

    @Override
    protected BowlingGame doBuildDomain(GameEntity entity) {
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
