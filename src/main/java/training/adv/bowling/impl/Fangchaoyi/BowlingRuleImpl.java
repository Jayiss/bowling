package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.util.ArrayList;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        if(newPins[0] < 0) return false;
        return existingTurns[existingTurns.length - 1].getSecondPin() != 0 || newPins[0] + existingTurns[existingTurns.length - 1].getFirstPin() <= getMaxPin() || existingTurns[existingTurns.length - 1].getFirstPin() == getMaxPin();
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return turn.getFirstPin().equals(getMaxPin());
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return !isStrike(turn) && turn.getFirstPin() + turn.getSecondPin() == getMaxPin();
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if(isStrike(turn)) return false;
        return turn.getFirstPin() + turn.getSecondPin() != getMaxPin();
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        return null;
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
        int len = (allTurns.length < getMaxTurn() ? allTurns.length : getMaxTurn());
        Integer[] totalScore = new Integer[len];
        Integer eachScore;
        for (int i = 0; i < len; i++) {
            eachScore = calScoreEach(i, allTurns);
            //if (eachScore == -1) return -1;
            totalScore[i] = eachScore;
        }

        return totalScore;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        return turn.getFirstPin() > 0 && turn.getSecondPin() > 0 && turn.getFirstPin() + turn.getSecondPin() <= getMaxPin();
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        BowlingTurn[] extendTurns = new BowlingTurnImpl[existingTurns.length];
        if(isNewPinsAllowed(existingTurns,pins)){
            if(existingTurns[0].getFirstPin() == 0){ //existingTurns is empty
                for(int i = 0,j=0;i < pins.length;i++,j++){
                    if(i+1 < pins.length && pins[i] != getMaxPin()){ //spare or miss
                        existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],pins[i+1]}));
                        //existingTurns[j] = new BowlingTurnImpl(new int[]{pins[i],pins[i+1]});
                        i++;
                    }
                    else if(pins[i] == getMaxPin()){ //next is strike
                        existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],0}));
                        //existingTurns[j] = new BowlingTurnImpl(new int[]{pins[i],0});
                    }
                    else
                        existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],0}));
                        //existingTurns[j] = new BowlingTurnImpl(new int[]{pins[i],0});
                }
                return existingTurns;
            }
            else if(!isStrike(existingTurns[existingTurns.length-1])){
                if(existingTurns[existingTurns.length-1].getSecondPin() == 0){
                    //add in previous turn
                    int tempPin = existingTurns[existingTurns.length-1].getFirstPin();
                    existingTurns[existingTurns.length-1] = new BowlingTurnImpl(new int[]{tempPin, pins[0]});
                    for(int i = 1;i < pins.length;i++){
                        if(i+1 < pins.length && pins[i] != getMaxPin()) {
                            existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],pins[i+1]}));
                            i++;
                        }
                        else{
                            existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],0}));
                        }
                    }
                    return existingTurns;
                }
                else{//add in next turn
                    for(int i = 0;i<pins.length;i++){
                        if(i+1 < pins.length && pins[i] != getMaxPin()) {
                            existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],pins[i+1]}));
                            i++;
                        }
                        else{
                            existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],0}));
                        }
                    }
                    return existingTurns;
                }
//                int i;
//                BowlingTurn turn = new BowlingTurnImpl(new int[]{0, pins[0]});
//                extendTurns = new BowlingTurnImpl[existingTurns.length+1];
//                extendTurns[existingTurns.length] = turn;
//                System.arraycopy(existingTurns,0,extendTurns,0,existingTurns.length);
//                for(i = 1;i <= pins.length;i+=2){
//                    BowlingTurn[] tmp = extendTurns;
//                    extendTurns = new BowlingTurnImpl[extendTurns.length+1];
//                    if(i+1 <= pins.length) {
//                        extendTurns[extendTurns.length-1] = new BowlingTurnImpl(new int[]{pins[i], pins[i+1]});
//                    }else extendTurns[extendTurns.length-1] = new BowlingTurnImpl(new int[]{pins[i], 0});
//                    System.arraycopy(tmp,0,extendTurns,0,tmp.length); //can simplify
//                }
            }else{ //previous is strike
                for(int i = 0;i < pins.length;i++){
                    extendTurns = new BowlingTurnImpl[existingTurns.length+1];
                    if(i+1 < pins.length && pins[i] != getMaxPin()) { //add a miss or spare
                        existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],pins[i+1]}));
                        i++;
                        //extendTurns[extendTurns.length-1] = new BowlingTurnImpl(new int[]{pins[i], pins[i+1]});
                    }
                    else existingTurns = addTurn(existingTurns, new BowlingTurnImpl(new int[]{pins[i],0}));
                    //else existingTurns = addTurn(existingTurns,new BowlingTurnImpl(new int[]{}))
//                        extendTurns[extendTurns.length-1] = new BowlingTurnImpl(new int[]{pins[i], 0});
                }
                return existingTurns;
            }
        } else return existingTurns;
    }

    @Override
    public Integer getMaxTurn() {
        return 10;
    }

    public Integer calScoreEach(int roundNum, BowlingTurn[] allTurns){
        Integer score = 0;
        if(isSpare(allTurns[roundNum])){
            if(roundNum < allTurns.length - 1) score += getMaxPin() + allTurns[roundNum+1].getFirstPin();
            else score += getMaxPin();
            return score;
        }
        if(isMiss(allTurns[roundNum])){
            return allTurns[roundNum].getFirstPin() + allTurns[roundNum].getSecondPin();
        }else { //strike
            if(roundNum == getMaxTurn() - 1){ //final turn
                if(allTurns.length == getMaxTurn()){
                    score += getMaxPin();
                    return score;
                }
                if(isStrike(allTurns[getMaxTurn()])) score += getMaxPin() + allTurns[getMaxTurn()].getFirstPin() + allTurns[getMaxTurn()+1].getFirstPin();
                else {
                    //TODO judge if allTurns[getMaxTurn()] valid
                    score += getMaxPin() + allTurns[roundNum+1].getFirstPin() + allTurns[roundNum+1].getSecondPin();
                }
                return score;
            }else {  //not the last turn
                if(roundNum < allTurns.length - 2) score += getMaxPin() + allTurns[roundNum+1].getFirstPin() + (isStrike(allTurns[roundNum+1]) ? allTurns[roundNum+2].getFirstPin() : allTurns[roundNum+1].getSecondPin());
                else if(roundNum == allTurns.length - 2 && allTurns[roundNum+1].getFirstPin() == getMaxPin()) score += getMaxPin() + allTurns[roundNum+1].getFirstPin();
                else if(roundNum < allTurns.length - 1 && allTurns[roundNum+1].getFirstPin() != getMaxPin()) score+=getMaxPin()+allTurns[roundNum+1].getFirstPin()+allTurns[roundNum+1].getSecondPin();
                else score += getMaxPin();
            }
        }
        return score;
    }

    public BowlingTurn[] addTurn(BowlingTurn[] turns, BowlingTurn nextTurn){
        if(turns.length == 1 && turns[0].getFirstPin() == 0) {
            turns[0] = nextTurn;
            return turns;
        }
        int length = turns.length;
        BowlingTurn[] extendTurns = new BowlingTurnImpl[length+1];
        extendTurns[length] = nextTurn;
        System.arraycopy(turns,0,extendTurns,0,turns.length);
        return extendTurns;
    }
}
