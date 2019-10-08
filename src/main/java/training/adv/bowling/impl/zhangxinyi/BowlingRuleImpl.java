package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.Arrays;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {
    private static final int MAX_PIN = 10;
    private static final int MAX_TURN = 10;

    @Override
    //If ture, then bowlingturnimpl constructor(if ture, then game impl will call rule and then use the turn impl)
    // 2 situation: existingTurns finished or not, first use isFinished(isGameFinished(more special))
    // and use isValid
    //
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        if (newPins.length == 0) {
            return false;
        }
        if (existingTurns.length == 0) {
            BowlingTurn newTurn;
            if (newPins.length == 1) {
                newTurn = new BowlingTurnImpl(newPins[0]);
            } else {
                newTurn = new BowlingTurnImpl(newPins[0], newPins[1]);
            }
            if (isValid(newTurn)) {
                List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
                existingTurnsList.add(newTurn);
                existingTurns = existingTurnsList.toArray(new BowlingTurn[0]);
                calcScores(existingTurns);
                return true;
            }
        } else {
            BowlingTurn lastTurn = existingTurns[existingTurns.length - 1];
            if (isFinish(lastTurn)) {
                BowlingTurn newTurn;
                if (newPins.length == 1) {
                    newTurn = new BowlingTurnImpl(newPins[0]);
                } else {
                    newTurn = new BowlingTurnImpl(newPins[0], newPins[1]);
                }
                if (isValid(newTurn)) {
                    List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
                    existingTurnsList.add(newTurn);
                    existingTurns = existingTurnsList.toArray(new BowlingTurn[0]);
                    calcScores(existingTurns);
                    return true;
                }
            } else {
                BowlingTurn newTurn = new BowlingTurnImpl(lastTurn.getFirstPin(), newPins[0]);
                if (isValid(newTurn)) {
                    List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
                    existingTurnsList.add(newTurn);
                    existingTurns = existingTurnsList.toArray(new BowlingTurn[0]);
                    calcScores(existingTurns);
                    return true;
                }
            }
        }
        return false;
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
        Integer first = turn.getFirstPin();
        Integer second = turn.getSecondPin();
        if (first != null && first == MAX_PIN && second == null) {
            return true;
        } else if (first != null && second != null) {
            if (first >= 0 && first < MAX_PIN && second >= 0 && second <= MAX_PIN) {
                return first + second <= MAX_PIN;
            }
        }
        return false;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        if (allTurns.length >= MAX_TURN + 2) {
            return true;
        } else if (allTurns.length == (MAX_TURN + 1) && allTurns[MAX_TURN - 1].getSecondPin()!=null
                    && allTurns[MAX_TURN - 1].getFirstPin() + allTurns[MAX_TURN - 1].getSecondPin() == MAX_PIN) {
                return true;
        } else if (allTurns.length == MAX_TURN && allTurns[MAX_TURN - 1].getSecondPin()!=null
                && allTurns[MAX_TURN - 1].getFirstPin() + allTurns[MAX_TURN - 1].getSecondPin() < MAX_PIN) {
            return true;
        } else {
            return false;
        }
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
        return MAX_TURN;
    }
}
