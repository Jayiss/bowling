package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

public class BowlingRuleImpl implements BowlingRule {

    private static final int MAX_PIN = 10;  // Set default bowling game max pin to 10
    private static final int MAX_TURN = 10;  // Set default bowling game max turn to 10

    private Boolean isRegularTurn(BowlingTurn[] existingTurns, int cursor_AddedTurn) {
        if (existingTurns.length + cursor_AddedTurn == MAX_TURN) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        // Check if all new pins are positive integers
        for (Integer newPin : newPins) {
            if (newPin < 0 || newPin > MAX_PIN) {
                System.out.println("Invalid Pin Detected ! New toss pin must always be 0~10 !\n");
                return false;
            }
        }

        int cursor_Pin = 0, cursor_AddedTurn = 0;  // Cursor for newPins & existingTurns
        BowlingTurn lastNonbonusTurn = null, lastExtgTurn = null, currTurn = null;
        if (existingTurns.length > 0) {  // Have existing turn's record
            // Get the last non-bonus turn & existing turn
            if (existingTurns.length >= MAX_TURN) {
                lastNonbonusTurn = existingTurns[MAX_TURN - 1];
            }
            lastExtgTurn = existingTurns[existingTurns.length - 1];

            // Scenario I: Current new pin is last turn 2nd toss -> {lastExtgTurn.1stPin, newPins[0]}
            if (!isFinish(lastExtgTurn) && existingTurns.length <= MAX_PIN) {
                currTurn = new BowlingTurnImpl(lastExtgTurn.getFirstPin(), newPins[0]);
                if (isValid(currTurn)) {
                    cursor_Pin++;  // Move cursor to next pin
                    if (existingTurns.length == MAX_TURN) {
                        lastNonbonusTurn = currTurn;  // Reload last non-bonus turn
                    }
                } else {
                    System.out.println("Current turn is invalid !\n");
                    return false;
                }
            }
        }

        // Scenario II: Current new pin will be the 1st toss
        while (cursor_Pin < newPins.length) {  // Loop through the new pins
            // Scenario II/1: Current turn only has 1 toss -> {newPins[cursor_Pin], null}
            // * Possibilities: 1.1 isSTRIKE; 1.2 Last new pin; 1.3 Has reached max turn
            if (newPins[cursor_Pin] == MAX_PIN || cursor_Pin == newPins.length - 1 || existingTurns.length + cursor_AddedTurn >= MAX_PIN) {
                currTurn = new BowlingTurnImpl(newPins[cursor_Pin], null);
                if (isValid(currTurn)) {
                    cursor_Pin++;  // Move cursor to the next pin
                    cursor_AddedTurn++;  // A new turn has been accepted
                    if (isRegularTurn(existingTurns, cursor_AddedTurn)) {
                        lastNonbonusTurn = currTurn;  // Reload last non-bonus turn
                    }
                } else {
                    System.out.println("Current turn is invalid !\n");
                    return false;
                }
                // Scenario II/2: Current turn consists of 2 tosses -> {newPins[n], newPins[n+1]}
            } else if (cursor_Pin < newPins.length - 1) {
                currTurn = new BowlingTurnImpl(newPins[cursor_Pin], newPins[cursor_Pin + 1]);
                if (isValid(currTurn)) {
                    cursor_Pin += 2;  // Move cursor to the pin after next
                    cursor_AddedTurn++;  // A new turn has been accepted
                    if (isRegularTurn(existingTurns, cursor_AddedTurn)) {
                        lastNonbonusTurn = currTurn;  // Reload last non-bonus turn
                    }
                } else {
                    System.out.println("Current turn is invalid !\n");
                    return false;
                }
            }
        }

        int len_CurrTurn = existingTurns.length + cursor_AddedTurn;
        // Judgement 10 turns: <10 turns / 10th turn is MISS
        if (lastNonbonusTurn == null || isMiss(lastNonbonusTurn)) {
            return (len_CurrTurn < MAX_TURN + 1);
            // Judgement 11 turns: 10th turn is SPARE
        } else if (isSpare(lastNonbonusTurn)) {
            return (len_CurrTurn < MAX_TURN + 2);
            // Judgement 12 turns: 10th turn is STRIKE
        } else {
            return (len_CurrTurn < MAX_TURN + 3);
        }
    }

    @Override
    // Check current turn
    public Boolean isValid(BowlingTurn currTurn) {
        Integer firstPin = currTurn.getFirstPin(), secondPin = currTurn.getSecondPin();
        if (currTurn.getSecondPin() != null) {  // Check (Sum < Max Pin)
            return (firstPin + secondPin <= MAX_PIN);
        }
        return true;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        if (isFinish(turn)) {
            return turn.getFirstPin() == MAX_PIN;
        }
        return false;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if (isFinish(turn) && turn.getSecondPin() != null) {
            return (turn.getFirstPin() + turn.getSecondPin() == MAX_PIN);
        }
        return false;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if (isFinish(turn) && turn.getSecondPin() != null) {
            return (!isSpare(turn));
        }
        return false;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {  // Check if current turn is finished
        return !(turn.getFirstPin() < MAX_PIN && turn.getSecondPin() == null);
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        if ((allTurns.length >= MAX_TURN + 2)  // Poss 1: >= 12 Turns
                || (allTurns.length == MAX_TURN + 1 && isSpare(allTurns[MAX_TURN - 1]))  // Poss 2: 11th Turn is SPARE
                || (allTurns.length == MAX_TURN && isMiss(allTurns[MAX_TURN - 1]))) {  // 10th Turn is MISS
            return true;
        }
        return false;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }

    private int calcTurnScore(BowlingTurn turn) {
        if (turn.getSecondPin() != null) {
            return (turn.getFirstPin() + turn.getSecondPin());
        } else {
            return turn.getFirstPin();
        }
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        Integer[] scores = new Integer[Math.min(allTurns.length, MAX_TURN)];  // 1d array for score of each turn

        for (int i = 0; i < Math.min(allTurns.length, MAX_TURN); i++) {  // All regular turns score
            scores[i] = calcTurnScore(allTurns[i]);
            if (i != allTurns.length - 1) {
                // Scenario I: Current turn is SPARE -> Add next 1 toss
                if (isSpare(allTurns[i])) {
                    scores[i] += allTurns[i + 1].getFirstPin();
                    // Scenario II: Current turn is STRIKE -> Add next 2 tosses
                } else if (isStrike(allTurns[i])) {
                    // Scenario II/1. Current toss is last but one toss -> Add next 1 toss only
                    if (i == allTurns.length - 2) {
                        scores[i] += calcTurnScore(allTurns[i + 1]);
                    } else {
                        // Scenario II/2: Next turn is STRIKE -> Add Turn_1.1stPin & Turn_2.1stPin
                        if (isStrike(allTurns[i + 1])) {
                            scores[i] += allTurns[i + 1].getFirstPin() + allTurns[i + 2].getFirstPin();
                        } else {
                            // Scenario II/3.1: Next turn is SPARE -> Add Turn_1.1stPin & Turn_1.2ndPin
                            if (isFinish(allTurns[i + 1])) {
                                scores[i] += calcTurnScore(allTurns[i + 1]);
                                // Scenario II/3.2: Next turn is Not Finished -> Add Turn_1.1stPin & Turn_2.1stPin
                            } else {
                                scores[i] += allTurns[i + 1].getFirstPin() + allTurns[i + 2].getFirstPin();
                            }
                        }
                    }
                }
            }
        }
        return scores;
    }

    private BowlingTurn[] addOneTurn(BowlingTurn[] existingTurns, BowlingTurn newTurn) {
        int len = existingTurns.length;
        BowlingTurn[] newTurns = new BowlingTurn[len + 1];
        System.arraycopy(existingTurns, 0, newTurns, 0, len);
        newTurns[len] = newTurn;
        return newTurns;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        int cursor_Pin = 0, len_ExtgTurn = existingTurns.length;
        BowlingTurn lastExtgTurn = null, currTurn = null;

        // Scenario I: Current new pin is last turn 2nd toss -> {lastExtgTurn.1stPin, newPins[0]}
        if (len_ExtgTurn > 0) {
            lastExtgTurn = existingTurns[len_ExtgTurn - 1];
            // Scenario I: Current new pin is last turn 2nd toss -> {lastExtgTurn.1stPin, newPins[0]}
            if (!isFinish(lastExtgTurn) && len_ExtgTurn <= MAX_TURN) {  // Reload lastExtgTurn
                existingTurns[len_ExtgTurn - 1] = new BowlingTurnImpl(lastExtgTurn.getFirstPin(), pins[0]);
                cursor_Pin++;
            }
        }

        // Scenario II: Current new pin will be the 1st toss
        while (cursor_Pin < pins.length) {
            // Scenario II/1: Current turn only has 1 toss -> {newPins[cursor_Pin], null}
            // * Possibilities: 1.1 isSTRIKE; 1.2 Last new pin; 1.3 Has reached max turn
            if (pins[cursor_Pin] == MAX_PIN || cursor_Pin == pins.length - 1 || len_ExtgTurn >= MAX_PIN) {
                currTurn = new BowlingTurnImpl(pins[cursor_Pin], null);
                existingTurns = addOneTurn(existingTurns, currTurn);
                cursor_Pin++;
                len_ExtgTurn++;
                // Scenario II/2: Current turn consists of 2 tosses -> {newPins[n], newPins[n+1]}
            } else if (cursor_Pin < pins.length - 1) {
                currTurn = new BowlingTurnImpl(pins[cursor_Pin], pins[cursor_Pin + 1]);
                existingTurns = addOneTurn(existingTurns, currTurn);
                cursor_Pin += 2;
                len_ExtgTurn++;
            }
        }
        return existingTurns;
    }
}
