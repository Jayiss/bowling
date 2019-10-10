package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame, BowlingGameEntity {

    private BowlingTurn[] turns = new BowlingTurn[0];
    private BowlingTurnEntity[] bowlingTurnEntities = new BowlingTurnEntity[0];
    private Integer id = new Integer(0);
//    private final long TIME_MILISTONE = 1570681594151l;

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
//        System.out.println(System.currentTimeMillis()-TIME_MILISTONE);
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
        return turns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns = super.rule.addScores(turns, pins);
        BowlingTurnEntity[] bowlingTurnEntities = new BowlingTurnEntity[turns.length];
        for (int i = 0; i < turns.length; i++) {
            bowlingTurnEntities[i] = turns[i].getEntity();
        }
        setTurnEntities(bowlingTurnEntities);
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
//        BowlingTurn[] tmpTurns = new BowlingTurn[turns.length];
//        for(int i=0;i<tmpTurns.length;i++)
//        {
//            tmpTurns[i]  = new BowlingTurnImpl(turns[i].getFirstPin(),turns[i].getSecondPin());
//        }
//        this.turns = tmpTurns;
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
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
