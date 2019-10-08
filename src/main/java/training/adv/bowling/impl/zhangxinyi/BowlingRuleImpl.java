package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

public class BowlingRuleImpl implements BowlingRule {
    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        return null;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        return null;
    }

    @Override
    public Integer getMaxPin() {
        return null;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        return null;
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        return new Integer[0];
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        return null;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        return new BowlingTurn[0];
    }

    @Override
    public Integer getMaxTurn() {
        return null;
    }
}
