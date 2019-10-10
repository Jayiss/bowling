package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame {

    private BowlingTurn []turns=new BowlingTurn[0];
    private Integer id = 0;

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
        return 1001;
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
            TurnKey turnKey=new TurnKeyImpl(i,1001);
            bowlingTurnEntity.setId(turnKey);
        }
        return list.toArray(new BowlingTurnEntity[0]);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }



    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
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
        return new BowlingTurn[0];
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns= super.rule.addScores(turns,pins);
        return null;
    }
}
