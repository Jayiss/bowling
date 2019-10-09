package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.Arrays;


public class BowlingRuleImpl implements BowlingRule {
    final int MAX_TURN = 10;
    final int MAX_PINS = 10;

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        return null;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return turn.getFirstPin()==MAX_PINS;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return turn.getFirstPin()+turn.getSecondPin()==MAX_PINS&&turn.getFirstPin()!=MAX_PINS;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        return turn.getFirstPin()+turn.getSecondPin()!=MAX_PINS;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        if(turn.getFirstPin()!=null&&turn.getSecondPin()!=null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PINS;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        int turn = 0;
        for(BowlingTurn bowlingTurn:allTurns){
            if(turn<MAX_TURN){
                if (bowlingTurn.getFirstPin()==null||bowlingTurn.getSecondPin()==null){
                    return false;
                }
                turn++;
            }else{//turn>=10:10,11
                //judge three condition
                if(isStrike(allTurns[MAX_TURN-1])){
                    if(isFinish(allTurns[MAX_TURN])){
                        //if it's strike
                        if(isStrike(allTurns[MAX_TURN])){
                            if(allTurns[MAX_TURN+1].getFirstPin()==null){
                                return false;
                            }
                        }
                    }else {
                        return false;
                    }
                }
                if(isSpare(allTurns[MAX_TURN-1])){
                     if(allTurns[MAX_TURN].getFirstPin()==null){
                         return false;
                     }
                }

            }
        }
        return true;
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        Integer[] scores = new Integer[MAX_TURN];
        int index = 0;
        for(;index<MAX_TURN;index++){
            if(isFinish(allTurns[index])){
                if(isStrike(allTurns[index])){
                    if(isFinish(allTurns[index+1])){
                        if(isStrike(allTurns[index+1])){
                            scores[index] = MAX_PINS+MAX_PINS+(allTurns[index+2].getFirstPin()==null?0:allTurns[index+2].getFirstPin());
                        }else {
                            scores[index]  = MAX_PINS+allTurns[index+1].getFirstPin()+allTurns[index+1].getSecondPin();
                        }
                    }else{
                        scores[index] = MAX_PINS+(allTurns[index+1].getFirstPin()==null?0:allTurns[index+1].getFirstPin());
                    }
                }
                if (isSpare(allTurns[index])){
                    //next fitst pin
                    Integer nextFistPin = allTurns[index+1].getFirstPin();
                    scores[index] = MAX_PINS + (nextFistPin==null?0:nextFistPin);
                }
                if(isMiss(allTurns[index])){
                    scores[index] = allTurns[index].getFirstPin()+allTurns[index].getSecondPin();
                }
            }else{
                //没有完成：
                if(allTurns[index].getFirstPin()!=null){
                    scores[index] = allTurns[index].getFirstPin();
                }else {
                    scores[index] = 0;
                }
            }
        }
        return scores;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        if(turn.getFirstPin()!=null&&turn.getSecondPin()!=null){
            if(turn.getFirstPin()<0||turn.getSecondPin()<0||(turn.getFirstPin()+turn.getSecondPin())>MAX_PINS){
                return false;
            }else {
                return true;
            }
        }else {
            if(turn.getFirstPin()<0||turn.getFirstPin()>MAX_PINS){
                return false;
            }else {
                return true;
            }
        }
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        //find the index we should add ;
        BowlingTurn[] beforeTurns = new BowlingTurnImpl[existingTurns.length];
        //for copy exsistingturns
        for(int i = 0;i<existingTurns.length;i++){
            beforeTurns[i] = existingTurns[i];
        }
        int index = 0;
        for(BowlingTurn bowlingTurn:existingTurns){
            if(isFinish(bowlingTurn)){
               index++;
            }
        }
        //having found the index starting add
        for(Integer i:pins){
            BowlingTurn newBowlingTurn;
            if(!isGameFinished(existingTurns)){
                BowlingTurn bowlingTurn = existingTurns[index];
                if(bowlingTurn.getFirstPin()!=null){
                     newBowlingTurn = new BowlingTurnImpl(bowlingTurn.getFirstPin(),i);
                    if(isValid(newBowlingTurn)){
                        Arrays.fill(existingTurns,index,index+1,newBowlingTurn);
                        index++;
                    }else {
                        return beforeTurns;
                    }
                }else {
                    if(i.equals(MAX_PINS)){
                        newBowlingTurn  = new BowlingTurnImpl(i,0);
                        if(isValid(newBowlingTurn)){
                            Arrays.fill(existingTurns,index,index+1,newBowlingTurn);

                        }else {
                            return beforeTurns;
                        }
                        index++;
                    }else{
                         newBowlingTurn = new BowlingTurnImpl(i,null);
                        if(isValid(newBowlingTurn)){
                            Arrays.fill(existingTurns,index,index+1,newBowlingTurn);

                        }else {
                            System.out.println("in");
                            return beforeTurns;
                        }
                    }

                }
            }else {
                return beforeTurns;
            }
        }
        return existingTurns;

    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}
