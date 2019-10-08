package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        for (int i = 0; i < newPins.length; i++) {
//            if (!isValid(new BowlingTurnImpl(lastExistingTurn.getFirstPin(), newPins[0])))
//                return false;
            if (newPins[i] < 0 || newPins[i] > getMaxPin())
                return false;
        }

        //check if add scores successful
        if (addScores(existingTurns, newPins) == null)
            return false;

        return true;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        if (turn.getSecondPin() == null && (turn.getFirstPin()) == getMaxPin())
            return true;
        else
            return false;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if (turn.getFirstPin() != null && turn.getSecondPin() != null && (turn.getFirstPin() + turn.getSecondPin()) == getMaxPin())
            return true;
        else
            return false;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if (turn.getFirstPin() != null && turn.getSecondPin() != null && (turn.getFirstPin() + turn.getSecondPin()) < getMaxPin())
            return true;
        else
            return false;
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
            else if (isSpare(allTurns[i]))
                currentScore = getMaxPin() + allTurns[i + 1].getFirstPin();
            else if (isStrike(allTurns[i])) {
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
        return scores.toArray(new Integer[scores.size()]);
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
        else if (turn.getSecondPin() != null && (turn.getFirstPin() + turn.getSecondPin() > 10))
            return false;
        else
            return true;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {

        boolean isExistingTurnsEmpty = (existingTurns.length == 0);
        BowlingTurn lastExistingTurn = null;
        if (!isExistingTurnsEmpty)
            lastExistingTurn = existingTurns[existingTurns.length - 1];

        ArrayList<BowlingTurn> tempTurns = new ArrayList<>();
        for (int i = 0; i < existingTurns.length; i++) {
            tempTurns.add(existingTurns[i]);
        }
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
//            } else if (i == pins.length && !isFinish(new BowlingTurnImpl(pins[i - 1], pins[i]))) {
//                return null;
            } else if (!isValid(new BowlingTurnImpl(pins[i - 1], pins[i]))) {
                return null;
            } else
                tempTurns.add(tempTurn);

            //if game finished
            if (i + 1 < pins.length && isGameFinished(tempTurns.toArray(new BowlingTurnImpl[tempTurns.size()])))
                return null;
        }

        return tempTurns.toArray(new BowlingTurnImpl[tempTurns.size()]);
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
