package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;
import training.adv.bowling.impl.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame {



    private BowlingTurn []turns=new BowlingTurn[0];
    private Integer id = 0;
    private Integer score;
    private Integer turnCount;

    public BowlingGameImpl(BowlingRule rule,int score,int turnCount) {
        super(rule);
        int gameId=1001;
        String sql="select max(id) from game";
        try{
            PreparedStatement ps= DBUtil.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>1000)
                gameId=rs.getInt(1)+1;
            this.id=gameId;

        }catch (Exception e){}
        this.id=gameId;
        this.score=score;
        this.turnCount=turnCount;
    }

    @Override
    public Integer getMaxPin() {
        return super.rule.getMaxPin();
    }

    @Override
    public Integer getMaxTurn() {
        return super.rule.getMaxTurn();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {


    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }



    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        List<BowlingTurnEntity> list=new ArrayList<>();
        BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
        for(int i=0;i<turns.length;i++)
        {
            bowlingTurnEntity.setFirstPin(turns[i].getFirstPin());
            bowlingTurnEntity.setSecondPin(turns[i].getSecondPin());
            TurnKey turnKey=new TurnKeyImpl(i,this.id);
            bowlingTurnEntity.setId(turnKey);
            list.add(bowlingTurnEntity);
        }
        return list.toArray(new BowlingTurnEntity[0]);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }



    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
        int gameId=1001;
        String sql="select max(id) from game";
        try{
            PreparedStatement ps= DBUtil.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>1000)
                gameId=rs.getInt(1)+1;
            this.id=gameId;

        }catch (Exception e){}
        this.id=gameId;
    }
    @Override
    public Integer getTotalScore() {
        Integer [] scores=getScores();
        Integer total_scores=0;
        for (Integer score:scores
        ) {
            total_scores+=score;
        }
        return total_scores;
    }

    @Override
    public Integer[] getScores() {
        return  super.rule.calcScores(turns);
    }

    @Override
    public BowlingTurn[] getTurns() {
        //在这里截取：
        BowlingTurn[] bowlingTurns1= Arrays.copyOf(turns,turns.length);
        return bowlingTurns1;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns= super.rule.addScores(turns,pins);
        turnCount=turns.length;
        return null;
    }

    public void setTurns(BowlingTurn[] turns) {
        this.turns = turns;
    }


}
