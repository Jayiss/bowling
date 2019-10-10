package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame {

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    @Override
    public Integer getMaxPin() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

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
    public void setTurnEntities(BowlingTurnEntity[] turns) {

    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return new BowlingTurnEntity[0];
    }

    @Override
    public Integer getMaxTurn() {
        return null;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return null;
    }
}
