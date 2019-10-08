package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.Arrays;

public class BowlingRuleImpl implements BowlingRule {
    private static final int MAX_PIN = 10;
    private static final int MAX_TURN = 10;

    @Override
    // According to turns already exist and incoming pins, check if accept new pins.
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        int existingTurnsNum = existingTurns.length;
        int potentialTurnsNum = 0;
        int index = 0;
        if (existingTurnsNum >= 1) {
            BowlingTurn lastTurn = existingTurns[existingTurnsNum - 1];
            if (!isFinish(lastTurn) && existingTurnsNum < MAX_TURN) {
                BowlingTurn temp = new BowlingTurnImpl(lastTurn.getFirstPin(), newPins[0]);
                if (isValid(temp)) {
                    index += 1;
                } else {
                    return false;
                }
            }
        }
        while (index < newPins.length) {
            if (newPins[index] == MAX_PIN || index == newPins.length - 1
                    || existingTurnsNum + potentialTurnsNum >= MAX_PIN) {
                BowlingTurn temp = new BowlingTurnImpl(newPins[index]);
                if (isValid(temp)) {
                    potentialTurnsNum += 1;
                    index += 1;
                } else {
                    return false;
                }
            } else if (index != newPins.length - 1) {
                BowlingTurn temp = new BowlingTurnImpl(newPins[index], newPins[index + 1]);
                if (isValid(temp)) {
                    potentialTurnsNum += 1;
                    index += 2;
                } else {
                    return false;
                }
            }
        }
        return existingTurnsNum + potentialTurnsNum <= MAX_TURN + 2;
    }

    @Override
    // Check if the given turn is a strike.
    // A turn need to be "finish" before "strike".
    public Boolean isStrike(BowlingTurn turn) {
        if (isFinish(turn)) {
            return turn.getFirstPin() == MAX_PIN;
        }
        return false;
    }

    @Override
    // Check if the given turn is a spare.
    public Boolean isSpare(BowlingTurn turn) {
        if (turn.getSecondPin() != null && isFinish(turn)) {
            return turn.getFirstPin() + turn.getSecondPin() == MAX_PIN;
        }
        return false;
    }

    @Override
    // Check if the given turn is a miss.
    public Boolean isMiss(BowlingTurn turn) {
        return turn.getSecondPin() != null && isFinish(turn) && !isSpare(turn);
    }

    @Override
    // check if the given turn is complete.
    // Can assume turn is valid.
    public Boolean isFinish(BowlingTurn turn) {
        return !(turn.getSecondPin() == null && turn.getFirstPin() < MAX_PIN);
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    // Check if game is finished according to turns already stored.
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        int len = allTurns.length;
        if (len >= MAX_TURN + 2) {
            return true;
        } else if (len == MAX_TURN + 1 && isSpare(allTurns[MAX_TURN - 1])) {
            return true;
        } else {
            return allTurns.length == MAX_TURN && isMiss(allTurns[MAX_TURN - 1]);
        }
    }

    @Override
    // Calculate the existed turns, and return the score array.
    // Notice existed turns can still be incomplete.
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        int len = allTurns.length;
        Integer[] scores = new Integer[Math.min(len, MAX_TURN)];
        // Calculate all the things beside the last 2 positions.
        for (int i = 0; i < Math.min(len, MAX_TURN); i++) {
            scores[i] = calcTurn(allTurns[i]);
            if (isSpare(allTurns[i]) && i != len - 1) {
                scores[i] += allTurns[i + 1].getFirstPin();
            }
            if (isStrike(allTurns[i]) && i != len - 1) {
                if (i == len - 2) {
                    scores[i] += calcTurn(allTurns[i + 1]);
                } else {
                    if (isStrike(allTurns[i + 1])) {
                        scores[i] += 10 + allTurns[i + 2].getFirstPin();
                    } else {
                        if (isFinish(allTurns[i + 1])) {
                            scores[i] += calcTurn(allTurns[i + 1]);
                        } else {
                            scores[i] += allTurns[i + 1].getFirstPin() + allTurns[i + 2].getFirstPin();
                        }
                    }
                }
            }
        }
        return scores;
    }

    private int calcTurn(BowlingTurn turn) {
        if (turn.getSecondPin() != null) {
            return turn.getFirstPin() + turn.getSecondPin();
        } else {
            return turn.getFirstPin();
        }
    }

    @Override
    // Check if the given turn's format is legal.
    // Notice turn can be incomplete.
    public Boolean isValid(BowlingTurn turn) {
        if (turn.getSecondPin() == null) {
            Integer first = turn.getFirstPin();
            return (first >= 0 && first <= MAX_PIN);
        } else {
            Integer first = turn.getFirstPin();
            Integer second = turn.getSecondPin();
            return (first >= 0 && first <= MAX_PIN && second > 0 && second <= MAX_PIN
                    && first + second <= MAX_PIN);
        }
    }

    @Override
    // Add legal input to the existed turns, and return all turns.
    // Not perfect version.
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        int existingTurnsNum = existingTurns.length;
        int index = 0;
        if (existingTurnsNum >= 1) {
            BowlingTurn lastTurn = existingTurns[existingTurnsNum - 1];
            if (!isFinish(lastTurn) && existingTurnsNum <= MAX_TURN) {
                existingTurns[existingTurnsNum - 1] = new BowlingTurnImpl(lastTurn.getFirstPin(), pins[0]);
                index += 1;
            }
        }
        while (index < pins.length) {
            if (pins[index] == MAX_PIN || index == pins.length - 1
                    || existingTurnsNum >= MAX_PIN) {
                BowlingTurn temp = new BowlingTurnImpl(pins[index]);
                existingTurns = addOneTurn(existingTurns, temp);
                existingTurnsNum += 1;
                index += 1;
            } else if (index != pins.length - 1) {
                BowlingTurn temp = new BowlingTurnImpl(pins[index], pins[index + 1]);
                existingTurns = addOneTurn(existingTurns, temp);
                existingTurnsNum += 1;
                index += 2;
            }
        }

        return existingTurns;
    }

    // Help add a new turn to a turns array.
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
