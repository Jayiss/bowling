package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {
    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        return null;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        if(turn.getFirstPin() == getMaxPin()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if(turn.getFirstPin()+turn.getSecondPin() == getMaxPin() && turn.getFirstPin() != getMaxPin()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if(turn.getFirstPin() + turn.getSecondPin() != getMaxPin()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        if(turn.getFirstPin() < getMaxPin() && turn.getSecondPin() == 0){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer getMaxPin() {
        return 10;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        return null;
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        List<Integer> turnScores = new ArrayList<>();
        for(int i = 0; i < allTurns.length; i++){
            boolean strike = false;
            boolean spare = false;
            boolean miss = false;
            if(i + 1 <= getMaxTurn()) {
                boolean finish = isFinish(allTurns[i]);
                if (finish == false) {
                    turnScores.add(allTurns[i].getFirstPin());
                } else {
                    strike = isStrike(allTurns[i]);
                    spare = isSpare(allTurns[i]);
                    miss = isMiss(allTurns[i]);
                    if (miss == true) {
                        turnScores.add(allTurns[i].getFirstPin() + allTurns[i].getSecondPin());
                    } else if (spare == true) {
                        if (i + 1 < allTurns.length) {
                            turnScores.add(allTurns[i].getFirstPin() + allTurns[i].getSecondPin() + allTurns[i + 1].getFirstPin());
                        } else {
                            turnScores.add(allTurns[i].getFirstPin() + allTurns[i].getSecondPin());
                        }
                    } else if (strike == true) {
                        if(i+2 <= allTurns.length && allTurns[i+1].getSecondPin() != 0){
                            turnScores.add(allTurns[i].getFirstPin()+allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin());
                        } else if(i+3 <= allTurns.length && allTurns[i+1].getSecondPin() == 0){
                            turnScores.add(allTurns[i].getFirstPin()+allTurns[i+1].getFirstPin()+allTurns[i+2].getFirstPin());
                        } else if(i+2 <= allTurns.length && allTurns[i+1].getSecondPin() == 0){
                            turnScores.add(allTurns[i].getFirstPin()+allTurns[i+1].getFirstPin());
                        } else{
                            turnScores.add(allTurns[i].getFirstPin());
                        }
                    }
                }
            }
        }
        return turnScores.toArray(new Integer[turnScores.size()]);
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        if(turn.getSecondPin() != 0){
            if(turn.getFirstPin() + turn.getSecondPin() > getMaxPin()){
                return false;
            }
        } else {
            if (turn.getFirstPin() > getMaxPin()){
                return false;
            }
        }
        return true;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        for(int i = 0; i < pins.length; i++){
            if(pins[i]<0){
                return new BowlingTurn[0];
            }
        }
        List<Integer> existingPinsList = turns2pins(existingTurns);
        List<Integer> newPinsList = new ArrayList<>(Arrays.asList(pins));
        BowlingTurn[] newTurns = pins2turns(newPinsList);
        for(int i = 0; i < newTurns.length; i++){
            if(isValid(newTurns[i]) == false){
                return pins2turns(existingPinsList);
            }
        }
        existingPinsList.addAll(newPinsList);
        BowlingTurn[] turns = pins2turns(existingPinsList);

        if (turns.length > 10) {
            if (isStrike(turns[9]) == true) {
                int count = 0;
                for (int i = 10; i < turns.length; i++) {
                    if (turns[i].getSecondPin() != 0) {
                        count += 2;
                    } else {
                        count += 1;
                    }
                    if (count > 2) {
                        return existingTurns;
                    }
                }
            }
            if(isSpare(turns[9]) == true) {
                int count = 0;
                for (int i = 10; i < turns.length; i++) {
                    if (turns[i].getSecondPin() != 0) {
                        count += 2;
                    } else {
                        count += 1;
                    }
                    if (count > 1) {
                        return existingTurns;
                    }
                }
            }
        }
        return turns;
    }

    @Override
    public Integer getMaxTurn() {
        return 10;
    }

    public List<Integer> turns2pins(BowlingTurn[] existingTurns) {
        List<Integer> pins = new ArrayList<>();
        if(existingTurns == null){
            return pins;
        }
        for (int i = 0; i < existingTurns.length; i++) {
            pins.add(existingTurns[i].getFirstPin());
            if(existingTurns[i].getSecondPin() != 0) {
                pins.add(existingTurns[i].getSecondPin());
            }
        }
        return pins;
    }

    public BowlingTurn[] pins2turns(List<Integer> pins){
        List<BowlingTurn> turns = new ArrayList<>();
        for (int i = 0; i < pins.size(); i++){
            if(pins.get(i) == getMaxPin()){
                BowlingTurnImpl turn = new BowlingTurnImpl();
                turn.setFirstPin(pins.get(i));
                turns.add(turn);
            } else if(i+1 == pins.size()) {
                BowlingTurnImpl turn = new BowlingTurnImpl();
                turn.setFirstPin(pins.get(i));
                turns.add(turn);
            } else {
                BowlingTurnImpl turn = new BowlingTurnImpl();
                turn.setFirstPin(pins.get(i));
                turn.setSecondPin(pins.get(i+1));
                i = i + 1;
                turns.add(turn);
            }
        }
        return turns.toArray(new BowlingTurn[turns.size()]);
    }
}
