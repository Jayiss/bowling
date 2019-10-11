package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

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
        for (BowlingTurnEntity value:bowlingTurnEntitys
             ) {
            BowlingTurn bowlingTurn=new BowlingTurnImpl(value.getFirstPin(),value.getSecondPin());
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
            ps.setInt(3,pins);
            ps.setInt(4,turns);
            ps.execute();
        }
        catch (Exception e){}


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
