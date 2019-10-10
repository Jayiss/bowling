package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {
    private final int MAXPIN =10;
    private final int MAXTURN =10;
    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer... newPins) {
        //pin<0||pin>10
        for (int i = 0; i < newPins.length; i++) {
            if (newPins[i]<0||newPins[i]>getMAXPIN()){
                return false;
            }
        }
        //check sum of two pin
        int startIndex=0;
        //check right pins`s times--for right finish pins

        int curNumberTurns=existingTurns==null?0: existingTurns.length;
        if (existingTurns!=null){
            if (existingTurns[existingTurns.length-1].getSecondPin()==-1&&existingTurns[existingTurns.length-1].getFirstPin()!=10){
                startIndex=1;
            }
        }
        if (curNumberTurns==10){
            if (newPins.length>2&&existingTurns[getMAXTURN()-1].getFirstPin()==10){
                return false;
            }
            if (newPins.length>1&&existingTurns[getMAXTURN()-1].getFirstPin()+existingTurns[getMAXTURN()-1].getSecondPin()==10){
                return false;
            }
            if (newPins.length>1&&existingTurns[getMAXTURN()-1].getFirstPin()+existingTurns[getMAXTURN()-1].getSecondPin()<10&&
                    existingTurns[getMAXTURN()-1].getSecondPin()!=-1){
                return false;
            }
        }
        for (int i = startIndex; i < newPins.length; i++) {

            if (newPins[i]<10){
                i++;
                //
                curNumberTurns++;
                if (newPins.length>i&&newPins[i]+newPins[i-1]>10){
                    if (curNumberTurns>=10){
                        curNumberTurns++;
                    }
                    return false;
                }
            }else{
                curNumberTurns++;
            }

        }
        //pins too lang
        BowlingTurn []turns=null;
        if (existingTurns!=null){
            List <Integer>ls=turns2pins(existingTurns);
            ls.addAll(Arrays.asList(newPins));
            List< BowlingTurn>lsB=pinsToturns(0, ls.toArray(new Integer[ls.size()]));
            turns= lsB.toArray(new  BowlingTurn[lsB.size()]);
            if (turns.length>12){
                return false;
            }
            if (turns.length>10){
                if (turns[getMAXTURN()].getFirstPin()+turns[getMAXTURN()].getSecondPin()==10&&turns.length>11){
                    return false;
                }
                if (turns[getMAXTURN()].getFirstPin()+turns[getMAXTURN()].getSecondPin()<10&&turns.length>10
                        &&turns[getMAXTURN()].getSecondPin()!=-1){
                    return false;
                }

            }
        }

        return true;
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
    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return turn.getFirstPin()==10?true:false;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if (turn.getFirstPin()==10){
            return false;
        }
        if (turn.getFirstPin()+turn.getSecondPin()==10){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {

        if (turn.getSecondPin()+turn.getFirstPin()<getMAXPIN()&&turn.getSecondPin()!=-1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        return null;
    }

    @Override
    public Integer getMAXPIN() {
        return MAXPIN;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        return null;
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        List<Integer>list=new ArrayList<>();
        for (int i = 0; i < allTurns.length&&i< getMAXTURN(); i++) {
            int curScore=0;
            if (isStrike(allTurns[i])){
                curScore+=10;
                //越界检查
                if (i+1<allTurns.length){
                    curScore+=allTurns[i+1].getFirstPin();
                    if (allTurns[i+1].getSecondPin()!=-1){
                        curScore+=allTurns[i+1].getSecondPin();
                    }else if(i+2<allTurns.length){
                        curScore+=allTurns[i+2].getFirstPin();
                    }
                }
                list.add(curScore);
                continue;
            }
            if (isSpare(allTurns[i])){
                curScore= 10;
                    //越界检查
                    if (i+1<allTurns.length){
                        curScore+=allTurns[i].getFirstPin();
                    }
                    list.add(curScore);
                continue;
            }
            if (isMiss(allTurns[i])){
                list.add(allTurns[i].getFirstPin()+allTurns[i].getSecondPin());
                continue;
            }
            list.add(allTurns[i].getFirstPin());
        }
        return list.toArray(new Integer[list.size()]);
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        if (turn.getFirstPin()==10&&turn.getSecondPin()==-1){
            return true;
        }
        if (turn.getFirstPin()+turn.getSecondPin()>10){
            return false;
        }
        return true;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        int startIndex=0;
        if (existingTurns!=null){
            BowlingTurnImpl bowlingTurn= (BowlingTurnImpl)existingTurns[existingTurns.length-1];

            int lastIndexTurnsLength=bowlingTurn.getLength();
            if (bowlingTurn.getSecondPin()==-1&&bowlingTurn.getFirstPin()!=10){
                ((BowlingTurnImpl)existingTurns[existingTurns.length-1]).setSecondPin(pins[0]);
                startIndex=1;
            }else {
                startIndex = 0;
            }
        }else{
            startIndex=0;
        }
        List<BowlingTurn>bl= pinsToturns(startIndex,pins);
        if (existingTurns!=null){
            bl.addAll(0, Arrays.asList(existingTurns) );
        }
        return bl.toArray(new BowlingTurn[bl.size()]);
    }
    public List<BowlingTurn> pinsToturns(int index,Integer...pins){
        List<BowlingTurn>bowlingTurnList=new ArrayList<>();
        for (int i = index; i < pins.length; i++) {
            if (pins[i]==this.getMAXPIN()){
                bowlingTurnList.add(new BowlingTurnImpl(pins[i]) );
            }else if (pins[i]==-1){
                continue;
            }else{
                if (i+1<pins.length){
                    bowlingTurnList.add(new BowlingTurnImpl( pins[i],pins[i+1]));
                    i++;
                }else{
                    bowlingTurnList.add(new BowlingTurnImpl(pins[i]));
                }
            }
        }
        return bowlingTurnList;
    }
    @Override
    public Integer getMAXTURN() {
        return MAXTURN;
    }
}
