package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;
import java.util.UUID;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame, BowlingGameEntity {

    private BowlingTurn[] turns = new BowlingTurn[0];
    private BowlingTurnEntity[] bowlingTurnEntities = new BowlingTurnEntity[0];
    private String id = UUID.randomUUID().toString();

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    @Override
    public Integer getTotalScore() {
        return sumArray(getScores());
    }

    @Override
    public Integer[] getScores() {
        return super.rule.calcScores(getTurns());
    }

    @Override
    public BowlingTurn[] getTurns() {
        //return copyTurns to avoid editing
        BowlingTurn[] copyTurns = new BowlingTurn[this.turns.length];
        for(int i=0;i<this.turns.length;i++)
        {
            copyTurns[i]=(new BowlingTurnImpl(0,0));
            copyTurns[i].getEntity().setId(this.turns[i].getEntity().getId());
            copyTurns[i].getEntity().setFirstPin(this.turns[i].getEntity().getFirstPin());
            copyTurns[i].getEntity().setSecondPin(this.turns[i].getEntity().getSecondPin());
        }
        return copyTurns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns= super.rule.addScores(turns, pins);
        //set turns' ids
        for(int i=0;i<turns.length;i++)
        {
            turns[i].getEntity().setId(new TurnKeyImpl(String.valueOf(i),this.getId()));
        }
        return getScores();
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }


    private int sumArray(Integer[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

    @Override
    public Integer getMaxPin() {
        return super.rule.getMaxPin();
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        //sync turnEntities with this.turns
        this.turns = new BowlingTurn[turns.length];
        for (int i = 0; i < this.turns.length; i++) {
            this.turns[i]=turns[i].getSecondPin() == null?new BowlingTurnImpl(turns[i].getFirstPin()):new BowlingTurnImpl(turns[i].getFirstPin(), turns[i].getSecondPin());
            this.turns[i].getEntity().setId(turns[i].getId());
        }
        this.bowlingTurnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return this.bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return super.rule.getMaxTurn();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
