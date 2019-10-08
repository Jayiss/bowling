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
        return turn.getSecondPin() != null && turn.getFirstPin() + turn.getSecondPin() == MAX_PIN;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if (!isFinish(turn)) {
            return false;
        }
        return turn.getSecondPin() != null && turn.getFirstPin() + turn.getSecondPin() < MAX_PIN;
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
        } else if (allTurns.length == (MAX_TURN + 1) && isSpare(allTurns[MAX_TURN - 1])) {
            return true;
        } else return allTurns.length == MAX_TURN && isMiss(allTurns[MAX_TURN - 1]);
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        Integer[] arr = new Integer[allTurns.length];
        for (int i = 0; i < allTurns.length - 2; i++) {
            if (isMiss(allTurns[i])) {
                arr[i] = allTurns[i].getFirstPin() + allTurns[i].getSecondPin();
            } else if (isSpare(allTurns[i])) {
                arr[i] = 10 + allTurns[i + 1].getFirstPin();
            } else {
                arr[i] = 10 + allTurns[i + 1].getFirstPin() + allTurns[i + 1].getSecondPin();
            }
        }

        if (isStrike(allTurns[allTurns.length - 2])) {
            arr[allTurns.length - 2] = 10 + allTurns[allTurns.length - 1].getFirstPin();
            if (allTurns[allTurns.length - 1].getSecondPin() != null) {
                arr[allTurns.length - 2] += allTurns[allTurns.length - 1].getSecondPin();
            }
        } else if (isSpare(allTurns[allTurns.length - 2])) {
            arr[allTurns.length - 2] = 10 + allTurns[allTurns.length - 1].getFirstPin();
        } else {
            arr[allTurns.length - 2] =
                    allTurns[allTurns.length - 2].getFirstPin() + allTurns[allTurns.length - 2].getSecondPin();
        }

        if (!isFinish(allTurns[allTurns.length - 1])) {
            arr[allTurns.length - 1] = allTurns[allTurns.length - 1].getFirstPin();
        } else if (isStrike(allTurns[allTurns.length - 1])) {
            arr[allTurns.length - 1] = 10;
        } else {
            arr[allTurns.length - 1] =
                    allTurns[allTurns.length - 1].getFirstPin() + allTurns[allTurns.length - 1].getSecondPin();
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
        if (pins.length == 0) {
            return existingTurns;
        }
        if (existingTurns.length == 0) {
            BowlingTurn newTurn;
            if (pins.length == 1) {
                newTurn = new BowlingTurnImpl(pins[0]);
            } else {
                newTurn = new BowlingTurnImpl(pins[0], pins[1]);
            }
            if (isValid(newTurn)) {
                List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
                existingTurnsList.add(newTurn);
                existingTurns = existingTurnsList.toArray(new BowlingTurn[0]);
                calcScores(existingTurns);
                return existingTurns;
            }
        } else {
            BowlingTurn lastTurn = existingTurns[existingTurns.length - 1];
            if (isFinish(lastTurn)) {
                BowlingTurn newTurn;
                if (pins.length == 1) {
                    newTurn = new BowlingTurnImpl(pins[0]);
                } else {
                    newTurn = new BowlingTurnImpl(pins[0], pins[1]);
                }
                if (isValid(newTurn)) {
                    List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
                    existingTurnsList.add(newTurn);
                    existingTurns = existingTurnsList.toArray(new BowlingTurn[0]);
                    calcScores(existingTurns);
                    return existingTurns;
                }
            } else {
                BowlingTurn newTurn = new BowlingTurnImpl(lastTurn.getFirstPin(), pins[0]);
                if (isValid(newTurn)) {
                    List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
                    existingTurnsList.add(newTurn);
                    existingTurns = existingTurnsList.toArray(new BowlingTurn[0]);
                    calcScores(existingTurns);
                    return existingTurns;
                }
            }
        }
        return existingTurns;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}
