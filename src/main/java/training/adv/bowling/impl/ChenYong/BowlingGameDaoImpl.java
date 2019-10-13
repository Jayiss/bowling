package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection){this.connection=connection;}
    @Override
    protected void doSave(BowlingGameEntity entity) {
         int id=entity.getId();
         int pins=entity.getMaxPin();
         int turns=entity.getMaxTurn();
         BowlingTurnEntity[] bowlingTurnEntitys =entity.getTurnEntities();

         //bowlingTurnsEntity convert to bowlingTurns
         List<BowlingTurn> bowlingTurnList=new ArrayList<>();
         for(int i=0;i<bowlingTurnEntitys.length;i++)
         {
            BowlingTurn bowlingTurn=new BowlingTurnImpl(bowlingTurnEntitys[i].getFirstPin(),bowlingTurnEntitys[i].getSecondPin(),bowlingTurnEntitys[i].getId());
            bowlingTurnList.add(bowlingTurn);
        }
        BowlingTurn[] bowlingTurns=bowlingTurnList.toArray(new BowlingTurn[0]);

        BowlingRule bowlingRule=new BowlingRuleImpl();
        Integer[] scores=bowlingRule.calcScores(bowlingTurns);

        Integer totalScore=0;
        for (Integer value:scores
             ) {
            totalScore+=value;
        }

        String sql="insert into game values (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setInt(2,totalScore);
            ps.setInt(3,turns);
            ps.setInt(4,pins);
            ps.execute();
        }
        catch (Exception e){
            int m=10;
            for(int i=0;i<10;i++)
                m+=10;
        }


    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        String sql="select * from game where id = ?";
        Integer resultId=0;
        Integer resultScore=0;
        Integer resultTurn=0;
        try{
            PreparedStatement ps= DBUtil.getConnection().prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next())
            {
                resultId=resultSet.getInt(1);
                resultScore=resultSet.getInt(2);
                resultTurn=resultSet.getInt(3);
            }
            connection.commit();
        }catch (Exception ex){}
        BowlingGameEntity bowlingGame=new BowlingGameImpl(new BowlingRuleImpl(),resultScore,resultTurn);
        bowlingGame.setId(resultId);

        sql="select * from turn where foreignid = ?";
        try{
            PreparedStatement ps=DBUtil.getConnection().prepareStatement(sql);
            ps.setInt(1,resultId);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                 Integer firstPin=rs.getInt(3);
                 Integer secondPin=rs.getInt(4);
                 ((BowlingGameImpl) bowlingGame).addScores(firstPin);
                 if(firstPin !=bowlingGame.getMaxPin())
                     ((BowlingGameImpl) bowlingGame).addScores(secondPin);
            }
        }catch (Exception e){}
        ((BowlingGameImpl) bowlingGame).getTotalScore();
        return bowlingGame;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return (BowlingGameImpl)entity;
    }

    @Override
    public boolean remove(Integer key) {
        String sql="delete game where id = ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,key);
            preparedStatement.executeUpdate();
            return true;

        }catch (Exception e){}
        return false;
    }
}
