package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame{

    private BowlingTurn[] turns = new BowlingTurn[0];

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
        return turns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns = super.rule.addScores(turns,pins);
        return getScores();
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }


    private int sumArray(Integer[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }
}
