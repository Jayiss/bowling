package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    @Override
    public Integer getTotalScore() {
        return null;
    }

    @Override
    public Integer[] getScores() {
        return new Integer[0];
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        return new Integer[0];
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
