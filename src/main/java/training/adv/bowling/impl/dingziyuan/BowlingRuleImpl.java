package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BowlingRuleImpl implements BowlingRule {
    private final Integer MAX_TURN;
    private final Integer MAX_PIN;

    public BowlingRuleImpl(Integer MAX_TURN, Integer MAX_PIN) {
        this.MAX_TURN = MAX_TURN;
        this.MAX_PIN = MAX_PIN;
    }


    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return turn.getSecondPin() == null && turn.getFirstPin() == MAX_PIN;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return turn.getSecondPin() != null && turn.getFirstPin() + turn.getSecondPin() == MAX_PIN;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        return turn.getSecondPin() != null && turn.getFirstPin() + turn.getSecondPin() < MAX_PIN;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        return turn.getSecondPin() != null || turn.getFirstPin() == MAX_PIN;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        for (BowlingTurn turn : allTurns) {
            if (!isFinish(turn))
                return false;
        }
        return true;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        return (turn.getFirstPin() + (turn.getSecondPin() == null ? 0 : turn.getSecondPin()) <= MAX_PIN);
    }


    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        Integer[] scores = new Integer[Math.min(allTurns.length, MAX_TURN)];
        for (int i = 0; i < scores.length; i++) {
            Integer score = 0;
            //SPARE
            if (isSpare(allTurns[i])) {
                score += (MAX_PIN + fetchFromNextRolls(allTurns, i, 1));
            }
            //STRIKE
            else if (isStrike(allTurns[i])) {
                score += (MAX_PIN + fetchFromNextRolls(allTurns, i, 2));
            } else {
                //MISS
                if (allTurns[i].getSecondPin() != null)
                    score += (allTurns[i].getFirstPin() + allTurns[i].getSecondPin());
                    //UNFINISHED
                else
                    score += (allTurns[i].getFirstPin());
            }
            scores[i] = score;
        }
        return scores;
    }


    //fetch next one or two rolls' down bottoms
    private int fetchFromNextRolls(BowlingTurn[] turns, int currentTurnIdx, int rollsNum) {
        BowlingTurn nextOne = (currentTurnIdx + 1 > turns.length - 1) ? new BowlingTurnImpl(0, 0) : turns[currentTurnIdx + 1];
        BowlingTurn nextSecond = (currentTurnIdx + 2 > turns.length - 1) ? new BowlingTurnImpl(0, 0) : turns[currentTurnIdx + 2];

        if (rollsNum == 1) {
            return nextOne.getFirstPin();
        } else if (rollsNum == 2) {
            if (nextOne.getSecondPin() == null) {
                return nextOne.getFirstPin() + nextSecond.getFirstPin();
            }
            if (nextOne.getSecondPin() != null) {
                return nextOne.getFirstPin() + nextOne.getSecondPin();
            }
        }
        return -1;
    }

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        if (existingTurns.length > MAX_TURN)
            return false;
        for (Integer pin : newPins) {
            if (!(pin >= 0 && pin <= MAX_PIN))
                return false;
        }
        return true;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        if (pins.length == 0)
            return existingTurns;
        if (!isNewPinsAllowed(existingTurns, pins))
            return existingTurns;

        List<BowlingTurn> existingTurnsList = Arrays.asList(existingTurns);
        List<Integer> existingPinsList = turns2pins(existingTurnsList);
        List<Integer> totalPinsList = new ArrayList<Integer>(existingPinsList);

        totalPinsList.addAll(Arrays.asList(pins));
        List<BowlingTurn> newTurnsList = pins2turns(totalPinsList);
        for (BowlingTurn turn : newTurnsList) {
            if (!isValid(turn))
                return existingTurns;
        }

        //may be one or two more pins
        if (newTurnsList.size() >= MAX_TURN) {
            int extraPinsNum = countPinsfromTurns(newTurnsList.size() > MAX_TURN ? newTurnsList.get(MAX_TURN) : null,
                    newTurnsList.size() > MAX_TURN + 1 ? newTurnsList.get(MAX_TURN + 1) : null);

            if (isSpare(newTurnsList.get(MAX_TURN - 1)))
                if (extraPinsNum > 1)
                    return existingTurns;
            if (isStrike(newTurnsList.get(MAX_TURN - 1)))
                if (extraPinsNum > 2)
                    return existingTurns;
            if (isMiss(newTurnsList.get(MAX_TURN - 1)))
                if (extraPinsNum > 0)
                    return existingTurns;
        }
        return (BowlingTurn[]) newTurnsList.toArray(new BowlingTurn[newTurnsList.size()]);
    }

    private Integer countPinsfromTurns(BowlingTurn... turns) {
        Integer cnt = 0;
        for (BowlingTurn turn : turns) {
            if (turn == null)
                continue;
            if (turn.getSecondPin() != null)
                cnt += 2;
            else
                cnt += 1;
        }
        return cnt;
    }

    private List<Integer> turns2pins(List<BowlingTurn> turns) {
        List<Integer> pins = new ArrayList<>();
        if (turns.isEmpty())
            return pins;

        for (BowlingTurn turn : turns) {
            pins.add(turn.getFirstPin());
            if (turn.getSecondPin() != null)
                pins.add(turn.getSecondPin());
        }
        return pins;
    }

    private List<BowlingTurn> pins2turns(List<Integer> pins) {
        List<BowlingTurn> turns = new ArrayList<>();
        Integer lastPin = null;
        for (int i = 0; i < pins.size(); i++) {
            if (lastPin != null) {
                turns.add(new BowlingTurnImpl(lastPin, pins.get(i)));
                lastPin = null;
                continue;
            }
            if (pins.get(i) == MAX_PIN) {
                turns.add(new BowlingTurnImpl(pins.get(i)));
                lastPin = null;
            } else {
                lastPin = pins.get(i);
                if (i + 1 == pins.size())
                    turns.add(new BowlingTurnImpl(pins.get(i)));
            }
        }
        return turns;
    }
}
