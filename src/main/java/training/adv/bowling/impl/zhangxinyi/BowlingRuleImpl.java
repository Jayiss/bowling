package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

public class BowlingRuleImpl implements BowlingRule {
    private static final int MAX_PIN = 10;
    private static final int MAX_TURN = 10;

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        if (newPins.length == 0) {
            return false;
        }
        if (existingTurns.length == 0) {//existingTurns is empty
            BowlingTurn newTurn;
            if (newPins.length == 1 || newPins[0] == MAX_PIN) {
                newTurn = new BowlingTurnImpl(newPins[0]);
            } else {
                newTurn = new BowlingTurnImpl(newPins[0], newPins[1]);
            }
            return isValid(newTurn);
        } else {// existingTurns is not empty
            BowlingTurn lastTurn = existingTurns[existingTurns.length - 1];
            if (isFinish(lastTurn)) {
                BowlingTurn newTurn;
                if (newPins.length == 1 || newPins[0] == MAX_PIN) {
                    newTurn = new BowlingTurnImpl(newPins[0]);
                } else {
                    newTurn = new BowlingTurnImpl(newPins[0], newPins[1]);
                }
                return isValid(newTurn);
            } else {
                BowlingTurn newTurn = new BowlingTurnImpl(lastTurn.getFirstPin(), newPins[0]);
                return isValid(newTurn);
            }
        }
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        if (!isFinish(turn)) {
            return false;
        }
        return turn.getSecondPin() == null;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if (!isFinish(turn)) {
            return false;
        }
        return (turn.getSecondPin() != null) && (turn.getFirstPin() + turn.getSecondPin() == MAX_PIN);
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if (!isFinish(turn)) {
            return false;
        }
        return (turn.getSecondPin() != null) && (turn.getFirstPin() + turn.getSecondPin() < MAX_PIN);
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        Integer first = turn.getFirstPin();
        Integer second = turn.getSecondPin();
        if (first == MAX_PIN && second == null) {
            return true;
        } else if (second != null) {
            return isValid(turn);
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
        } else if (allTurns.length == (MAX_TURN + 1) && isSpare(allTurns[MAX_TURN - 1])) {
            return true;
        } else return allTurns.length == MAX_TURN && isMiss(allTurns[MAX_TURN - 1]);
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        int len = allTurns.length;
        Integer[] arr = new Integer[len];
        for (int i = 0; i < len - 2; i++) {
            if (isMiss(allTurns[i])) {
                arr[i] = allTurns[i].getFirstPin() + allTurns[i].getSecondPin();
            } else if (isSpare(allTurns[i])) {
                arr[i] = 10 + allTurns[i + 1].getFirstPin();
            } else {
                arr[i] = 10 + allTurns[i + 1].getFirstPin() + allTurns[i + 1].getSecondPin();
            }
        }

        if (len >= 2) {
            if (isStrike(allTurns[len - 2])) {
                arr[len - 2] = 10 + allTurns[len - 1].getFirstPin();
                if (allTurns[len - 1].getSecondPin() != null) {
                    arr[len - 2] += allTurns[len - 1].getSecondPin();
                }
            } else if (isSpare(allTurns[len - 2])) {
                arr[len - 2] = 10 + allTurns[len - 1].getFirstPin();
            } else {// isMiss
                arr[len - 2] = allTurns[len - 2].getFirstPin() + allTurns[len - 2].getSecondPin();
            }
        }

        if (!isFinish(allTurns[len - 1])) {
            arr[len - 1] = allTurns[len - 1].getFirstPin();
        } else if (isStrike(allTurns[len - 1])) {
            arr[len - 1] = 10;
        } else {
            arr[len - 1] = allTurns[len - 1].getFirstPin() + allTurns[len - 1].getSecondPin();
        }
        return arr;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        if (turn.getSecondPin() == null) {
            Integer first = turn.getFirstPin();
            return (first > 0 && first <= MAX_PIN);
        } else {
            Integer first = turn.getFirstPin();
            Integer second = turn.getSecondPin();
            return (first > 0 && first <= MAX_PIN && second > 0 && second <= MAX_PIN && first + second <= MAX_PIN);
        }
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        if (existingTurns.length == 0) {
            BowlingTurn newTurn;
            if (pins.length == 1 || pins[0] == MAX_PIN) {
                newTurn = new BowlingTurnImpl(pins[0]);
            } else {
                newTurn = new BowlingTurnImpl(pins[0], pins[1]);
            }
            existingTurns = addOneTurn(existingTurns, newTurn);
            return existingTurns;
        } else {
            BowlingTurn lastTurn = existingTurns[existingTurns.length - 1];
            if (isFinish(lastTurn)) {
                BowlingTurn newTurn;
                if (pins.length == 1 || pins[0] == MAX_PIN) {
                    newTurn = new BowlingTurnImpl(pins[0]);
                } else {
                    newTurn = new BowlingTurnImpl(pins[0], pins[1]);
                }
                existingTurns = addOneTurn(existingTurns, newTurn);
                return existingTurns;
            } else {
                BowlingTurn newTurn = new BowlingTurnImpl(lastTurn.getFirstPin(), pins[0]);
                existingTurns = addOneTurn(existingTurns, newTurn);
                return existingTurns;
            }
        }
    }

    private BowlingTurn[] addOneTurn(BowlingTurn[] existingTurns, BowlingTurn newTurn) {
        int len = existingTurns.length;
        BowlingTurn[] newTurns = new BowlingTurn[len + 1];
        System.arraycopy(existingTurns, 0, newTurns, 0, len);
        newTurns[len] = newTurn;
        return newTurns;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}
