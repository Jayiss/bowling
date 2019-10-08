package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class BowlingRuleImpl implements BowlingRule {
    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {

        /*if new pins is empty, no action is necessary
         * if game is finished, no more pin record is allowed*/
        if (isGameFinished(existingTurns) || newPins.length == 0)
            return false;
        if (existingTurns.length == 0)
            return true;


        BowlingTurn lastExistingTurn = existingTurns[existingTurns.length - 1];

        //check if every digit of the pins input is in the range of 0 - MaxPin
        for (Integer newPin : newPins) {
//            if (!isValid(new BowlingTurnImpl(lastExistingTurn.getFirstPin(), newPins[0])))
//                return false;
            if (newPin < 0 || newPin > getMaxPin())
                return false;
        }

        //check if add scores successful
        return addScores(existingTurns, newPins) != null;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return turn.getSecondPin() == null && Objects.equals(turn.getFirstPin(), getMaxPin());
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return turn.getFirstPin() != null && turn.getSecondPin() != null && (turn.getFirstPin() + turn.getSecondPin()) == getMaxPin();
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        return turn.getFirstPin() != null && turn.getSecondPin() != null && (turn.getFirstPin() + turn.getSecondPin()) < getMaxPin();
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        if (!isValid(turn))
            return false;
        else
            return isMiss(turn) || isSpare(turn) || isStrike((turn));
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        if (allTurns.length < getMaxTurn())
            return false;
        else if (allTurns.length == getMaxTurn())
            return isMiss(allTurns[getMaxTurn() - 1]);
        else if (allTurns.length == getMaxTurn() + 1) {
            boolean isStrike = isStrike(allTurns[getMaxTurn() - 1]);
            boolean isSpare = isSpare(allTurns[getMaxTurn() - 1]);
            boolean strikeFinished = isStrike && allTurns[getMaxTurn()].getFirstPin() != null && allTurns[getMaxTurn()].getSecondPin() != null;
            boolean spareFinished =
                    isSpare && allTurns[getMaxTurn()].getFirstPin() != null && allTurns[getMaxTurn()].getSecondPin() == null;
            return strikeFinished || spareFinished;
        } else if (allTurns.length == getMaxTurn() + 2) {
            boolean isStrike = isStrike((allTurns[getMaxTurn() - 1]));
            return isStrike && allTurns[getMaxTurn()].getFirstPin() != null && allTurns[getMaxTurn() + 1].getFirstPin() != null && allTurns[getMaxTurn()].getSecondPin() == null && allTurns[getMaxTurn() + 1].getSecondPin() == null;
        }

        return null;//Game not valid
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        ArrayList<Integer> scores = new ArrayList<>();

        for (int i = 0; i < allTurns.length; i++) {
            int currentScore = 0;
            if (isMiss(allTurns[i]))
                currentScore = allTurns[i].getFirstPin() + allTurns[i].getSecondPin();
            else if (isSpare(allTurns[i])) {
                currentScore = getMaxPin();
                if (allTurns.length > i + 1 && allTurns[i + 1] != null && allTurns[i + 1].getFirstPin() != null)
                    currentScore += allTurns[i + 1].getFirstPin();
            } else if (isStrike(allTurns[i])) {
                currentScore = getMaxPin();

                int bonusScoreAdded = 0;
                if (allTurns.length > i + 1 && allTurns[i + 1] != null && allTurns[i + 1].getFirstPin() != null) {
                    currentScore += allTurns[i + 1].getFirstPin();
                    bonusScoreAdded++;
                }
                if (allTurns.length > i + 1 && allTurns[i + 1] != null && allTurns[i + 1].getFirstPin() != null && allTurns[i + 1].getSecondPin() != null) {
                    currentScore += allTurns[i + 1].getSecondPin();
                    bonusScoreAdded++;
                }
                if (allTurns.length > i + 2 && allTurns[i + 2] != null && allTurns[i + 2].getFirstPin() != null && bonusScoreAdded < 2)
                    currentScore += allTurns[i + 2].getFirstPin();

            } else {
                currentScore += allTurns[i].getFirstPin();
            }
            scores.add(currentScore);
        }
        return scores.toArray(new Integer[0]);
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {

        //each turn should contain one or two pin records
        if (turn == null || turn.getFirstPin() == null)
            return false;
        else if (turn.getFirstPin() > 10 || turn.getFirstPin() < 0)
            return false;
        else if (turn.getSecondPin() == null)
            return true;
        else if (turn.getSecondPin() > 10 || turn.getSecondPin() < 0)
            return false;
        else return turn.getSecondPin() == null || (turn.getFirstPin() + turn.getSecondPin() <= 10);
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {

        boolean isExistingTurnsEmpty = (existingTurns.length == 0);
        BowlingTurn lastExistingTurn = null;
        if (!isExistingTurnsEmpty)
            lastExistingTurn = existingTurns[existingTurns.length - 1];

        ArrayList<BowlingTurn> tempTurns = new ArrayList<>();
        Collections.addAll(tempTurns, existingTurns);
        /*first situation: last turn of the existingTurns is not finished
         * second situation: last turn of the existingTurns is finished*/
        boolean isLastExistingTurnFinished = isExistingTurnsEmpty || isFinish(lastExistingTurn);
        //if the last turn is not finished,make sure the first new pin input is valid
        if (!isLastExistingTurnFinished) {
            if ((lastExistingTurn.getFirstPin() + pins[0]) > 10) {
                return null;
            } else {
                tempTurns.remove(tempTurns.size() - 1);
                tempTurns.add(new BowlingTurnImpl(lastExistingTurn.getFirstPin(), pins[0]));
            }

        }

        //generate new turns with the new pin input, se if all the generated turns is legal
        BowlingTurn tempTurn = new BowlingTurnImpl();

        for (int i = isLastExistingTurnFinished ? 0 : 1; i < pins.length; i++) {
            if (isFinish(tempTurn) || tempTurn.getFirstPin() == null) {
                tempTurn = new BowlingTurnImpl(pins[i]);
            }

            //constructing temp turns
            if (!isValid(tempTurn)) {
                return null;
            } else if (isFinish(tempTurn)) {
                tempTurns.add(tempTurn);
                continue;
            } else if (!tempTurns.isEmpty()&&!isFinish(tempTurns.get(tempTurns.size() - 1))) {
                if (isValid(new BowlingTurnImpl(tempTurns.get(tempTurns.size() - 1).getFirstPin(),
                        pins[i]))) {
                    int firstPinOfLastTurn = tempTurns.get(tempTurns.size() - 1).getFirstPin();
                    tempTurns.remove(tempTurns.size() - 1);
                    tempTurns.add(new BowlingTurnImpl(firstPinOfLastTurn, pins[i]));
                } else {
                    return null;
                }
            } else
                tempTurns.add(tempTurn);

            //if game finished
            if (i + 1 < pins.length && isGameFinished(tempTurns.toArray(new BowlingTurn[0])))
                return null;
        }

        return tempTurns.toArray(new BowlingTurn[0]);
    }

    @Override
    public Integer getMaxPin() {
        return 10;
    }

    @Override
    public Integer getMaxTurn() {
        return 10;
    }
}
