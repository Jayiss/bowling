package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.TurnKey;


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
        return isGameFinished(allTurns,0);
    }

    private boolean isGameFinished(BowlingTurn[] bowlingTurns, int index){
        //当index为11的时候，通过第十轮来判断，
        if(index==MAX_TURN+1){
            if(isStrike(bowlingTurns[MAX_TURN-1])&&isStrike(bowlingTurns[MAX_TURN])){
                return bowlingTurns[MAX_TURN+1].getFirstPin()!=null;
            }
            return true;
        }
        if(isFinish(bowlingTurns[index])){
            return  isGameFinished(bowlingTurns,index+1);
        }else {
            return false;
        }
    }

    //还没来的及修改！
    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        Integer[] scores = new Integer[MAX_TURN];
        int index = 0;
        //之前的方法：
//        for(;index<MAX_TURN;index++){
//            if(isFinish(allTurns[index])){
//                if(isStrike(allTurns[index])){
//                    if(isFinish(allTurns[index+1])){
//                        if(isStrike(allTurns[index+1])){
//                            scores[index] = MAX_PINS+MAX_PINS+(allTurns[index+2].getFirstPin()==null?0:allTurns[index+2].getFirstPin());
//                        }else {
//                            scores[index]  = MAX_PINS+allTurns[index+1].getFirstPin()+allTurns[index+1].getSecondPin();
//                        }
//                    }else{
//                        scores[index] = MAX_PINS+(allTurns[index+1].getFirstPin()==null?0:allTurns[index+1].getFirstPin());
//                    }
//                }
//                if (isSpare(allTurns[index])){
//                    //next fitst pin
//                    Integer nextFistPin = allTurns[index+1].getFirstPin();
//                    scores[index] = MAX_PINS + (nextFistPin==null?0:nextFistPin);
//                }
//                if(isMiss(allTurns[index])){
//                    scores[index] = allTurns[index].getFirstPin()+allTurns[index].getSecondPin();
//                }
//            }else{
//                //没有完成：
//                if(allTurns[index].getFirstPin()!=null){
//                    scores[index] = allTurns[index].getFirstPin();
//                }else {
//                    scores[index] = 0;
//                }
//            }
//        }

        //已完成：当前的方法：
        //截取数组：
        for(;index<allTurns.length-1;index++){
            if(!isFinish(allTurns[index])){
                break;
            }
        }
        System.out.println(index);
//        当前的index是没有完成的那个，保证index前的都是finished；
        for(int i = 0;i<index&&i<MAX_TURN;i++){
            if(isStrike(allTurns[i])){
                System.out.println(i);
               if(i+1==index){
                   scores[i] = MAX_PINS + (allTurns[index].getFirstPin()==null?0:allTurns[i+1].getFirstPin());
               }else if(isStrike(allTurns[i+1])){
                   scores[i] = MAX_PINS + MAX_PINS + (allTurns[i+2].getFirstPin()==null?0:allTurns[i+2].getFirstPin());
               }else {
                   scores[i] = MAX_PINS + allTurns[i+1].getFirstPin() +allTurns[i+1].getSecondPin();
               }
            }
            if(isSpare(allTurns[i])){
                scores[i] = (allTurns[i+1].getFirstPin()==null?0:allTurns[i+1].getFirstPin())+ MAX_PINS;
            }
            if(isMiss(allTurns[i])){
                scores[i] = allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
            }
        }
        if(allTurns[index].getFirstPin()!=null&&index<MAX_TURN){
            scores[index] = allTurns[index].getFirstPin();
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
        BowlingTurn[] beforeTurns = new BowlingTurnImpl[existingTurns.length];
        //for copy exsistingturns
        for(int i = 0;i<existingTurns.length;i++){
            beforeTurns[i] = existingTurns[i];
        }
        //find the index we should add ;
        int index = 0;
        boolean isTenTurnStrike = false;
        boolean indexIncFlag  = false;
        for(; index<existingTurns.length;index++){
            if(!isFinish(existingTurns[index])){
               break;
            }
        }
        //having found the index starting add
        for(Integer i:pins){
            if(isGameFinished(existingTurns)){
             return beforeTurns;
            }
            BowlingTurn newBowlingTurn;
            BowlingTurn nowBowlingTurn = existingTurns[index];
            TurnKey turnKey = nowBowlingTurn.getEntity().getId();
            if(nowBowlingTurn.getFirstPin()!=null){
                newBowlingTurn = new BowlingTurnImpl(nowBowlingTurn.getFirstPin(),i,turnKey);
                index++;
                indexIncFlag = true;
            }else {
                if(i.equals(MAX_PINS)){
                    newBowlingTurn  = new BowlingTurnImpl(i,0,turnKey);
                    index++;
                    indexIncFlag = true;
                }else{
                    newBowlingTurn = new BowlingTurnImpl(i,null,turnKey);
                    indexIncFlag = false;
                }
            }
            if(!isValid(newBowlingTurn)){
                return beforeTurns;
            }
            int changeIndex = indexIncFlag?index-1:index;
            existingTurns[changeIndex] = newBowlingTurn;
            //当改变的index是maxturn的时候
            if(index==MAX_TURN){
                if(!existingTurns[MAX_TURN-1].getFirstPin().equals(10)){
                    existingTurns[MAX_TURN] =  new BowlingTurnImpl(existingTurns[MAX_TURN].getFirstPin(),0,turnKey);
                }
            }

        }

        return existingTurns;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}
