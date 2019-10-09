package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {

    private int MAX_PINS=10;
    private int MAX_TURNS=10;

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        return null;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        if(turn.getFirstPin()==10){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if(turn.getFirstPin()+turn.getSecondPin()==10){
            return true;
        }
        return false;

    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {

        if(turn.getFirstPin()+turn.getSecondPin()<10){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        if(turn.getSecondPin()!=null&&turn.getFirstPin()!=null){
            return true;
        }
        return false;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PINS;
    }

    private boolean isFinishBorder(BowlingTurn[] allTurns){
        if(allTurns.length==10&&isFinish(allTurns[9])&&isMiss(allTurns[9])){
            return true;
        }
        if(allTurns.length==11&&isFinish(allTurns[9])&&isSpare(allTurns[9])){
            Integer a=allTurns[10].getSecondPin();
            if(a==null||a==0){
                return true;
            }
        }
        if(allTurns.length==11&&isStrike(allTurns[9])&&isSpare(allTurns[10])){
            return true;
        }
        if(allTurns.length==12&&isStrike(allTurns[9])&&isStrike(allTurns[10])){
            Integer a=allTurns[11].getSecondPin();
            if(a==null||a==0){
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {

        if(allTurns.length<getMaxTurn()){
            return false;
        }
       if(isFinishBorder(allTurns)){
           return true;
       }
        return false;
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        if(allTurns[0].getFirstPin()==null){
            Integer[] i={0};
            return i;
        }

        int f;
        if(isGameFinished(allTurns)){
             f=MAX_TURNS;
        }else {
             f=allTurns.length;
        }
        Integer[] scores=new Integer[f];

        for(int i=0;i<f;i++){
            BowlingTurn currentturn=allTurns[i];
            int nextOne=0;
            int nextTwo=0;
            int score=0;

            if (isStrike(currentturn)) {
                if (i + 1 < allTurns.length) {
                    BowlingTurn nextTurn = allTurns[i + 1];
                    if (isStrike(nextTurn)) {
                        nextOne = MAX_PINS;
                        if (i + 2 < allTurns.length) {
                            nextTwo = allTurns[i + 2].getFirstPin();
                        }
                    } else {
                        nextOne = nextTurn.getFirstPin();
                        if(nextTurn.getSecondPin()!=null){
                            nextTwo = nextTurn.getSecondPin();
                        }
                    }
                }
                score=MAX_PINS+nextOne+nextTwo;
            }
            else{
                if(isFinish(currentturn)){
                    if(isSpare(currentturn)){
                        //find next one
                        if(i+1<allTurns.length) {
                            nextOne= allTurns[i+1].getFirstPin();
                        }
                        score=MAX_PINS+nextOne;
                    }else{
                        if(currentturn.getSecondPin()!=null){
                            score=currentturn.getFirstPin()+currentturn.getSecondPin();
                        }else {
                            score=currentturn.getFirstPin();
                        }

                    }
                }else{
                    score=currentturn.getFirstPin();
                }
            }

            scores[i]=score;
        }
        return scores;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {

        if(turn.getSecondPin()!=null){
            if(turn.getFirstPin()+turn.getSecondPin()>10){
                return false;
            }
            if(turn.getFirstPin()<0|turn.getSecondPin()<0){
                return false;
            }
        }else {
            if(turn.getFirstPin()>10){
                return false;
            }
            if(turn.getFirstPin()<0){
                return false;
            }
        }


        return true;
    }

    //function here is to get the current whole turn list
    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        if(pins.length==0){
            return existingTurns;
        }
        if(isGameFinished(existingTurns)){
            return existingTurns;
        }

        List<BowlingTurn> newTurns =new ArrayList<>();
        List<Integer> pinsT =Arrays.asList(pins);
        List<Integer> pinsA =new ArrayList<>();

        for(Integer t:pinsT){
            pinsA.add(t);
            if(t==10){
                pinsA.add(0);
            }
        }

        if(existingTurns[0].getFirstPin()==null){

            for(int j=0;j<pinsA.size()/2;j++){
                BowlingTurn bowlingTurn=new BowlingTurnImpl(pinsA.get(j*2),pinsA.get(j*2+1));
                if(!isValid(bowlingTurn)){
                    return existingTurns;
                }
                newTurns.add(bowlingTurn);
            }
            if(pinsA.size()%2==1){
                BowlingTurn bowlingTurn=new BowlingTurnImpl(pinsA.get(pinsA.size()-1));
                if(!isValid(bowlingTurn)){
                    return existingTurns;
                }
                newTurns.add(bowlingTurn);
            }
        }
        else{
            if(isFinish(existingTurns[existingTurns.length-1])){

                for(int i=0;i<existingTurns.length;i++){
                    newTurns.add(existingTurns[i]);
                }

                for(int j=0;j<pinsA.size()/2;j++){
                    BowlingTurn bowlingTurn=new BowlingTurnImpl(pinsA.get(j*2),pinsA.get(j*2+1));
                    if(!isValid(bowlingTurn)){
                        return existingTurns;
                    }
                    newTurns.add(bowlingTurn);
                }
                if(pinsA.size()%2==1){
                    BowlingTurn bowlingTurn=new BowlingTurnImpl(pinsA.get(pinsA.size()-1));
                    if(!isValid(bowlingTurn)){
                        return existingTurns;
                    }
                    newTurns.add(bowlingTurn);
                }
            }
            else {
                for(int i=0;i<existingTurns.length-1;i++){
                    newTurns.add(existingTurns[i]);
                }
                BowlingTurn bowlingTurn=new BowlingTurnImpl(existingTurns[existingTurns.length-1].getFirstPin(),pinsA.get(0));
                if(!isValid(bowlingTurn)){
                    return existingTurns;
                }
                newTurns.add(bowlingTurn);

                for(int j=1;j<=(pinsA.size()-1)/2;j++){
                    BowlingTurn bowlingTurn1=new BowlingTurnImpl(pinsA.get(j*2-1),pinsA.get(j*2));

                    if(!isValid(bowlingTurn1)){
                        return existingTurns;
                    }
                    newTurns.add(bowlingTurn1);
                }
                if(pinsA.size()%2==0){
                    BowlingTurn bowlingTurn2=new BowlingTurnImpl(pinsA.get(pinsA.size()-1));
                    if(!isValid(bowlingTurn2)){
                        return existingTurns;
                    }
                    newTurns.add(bowlingTurn2);
                }
            }
        }

        BowlingTurn[] result=newTurns.toArray(BowlingTurn[]::new);
        if(result.length>12||(result.length>10&!(isFinishBorder(result)))){
            return existingTurns;
        }

        return result;
    }




    @Override
    public Integer getMaxTurn() {
        return MAX_TURNS;
    }
}
