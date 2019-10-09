package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {

    public static final int maxPin=10;
    public static final int maxTurns=10;

    private static class InstanceHolder{
        private static BowlingRuleImpl instance=new BowlingRuleImpl();
    }

    public static BowlingRuleImpl getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        //assume existingTurns is legal
        List<BowlingTurn> list= new ArrayList();
        for (BowlingTurn turn :
                existingTurns) {
            list.add(turn);
        }
        for (Integer i :
                newPins) {
            if (!addPin(list,i))return false;
        }
        return true;
    }

    private boolean addPin(List<BowlingTurn> list,Integer pin){
        //assume list is legal
        if (!addable(list))return false;
        int size=list.size();
        if (size==0){
            return addNewPin(list,pin);
        }else {
            BowlingTurn temp=list.get(size-1);
            if (isFinish(temp)){//last turn is finished
                return addNewPin(list,pin);
            }else {
                BowlingTurnImpl temp2=new BowlingTurnImpl(temp.getFirstPin());
                temp2.setSecond(pin);
                if (isValid(temp2)){
                    list.set(size-1,temp2);
                    return true;
                }
                else return false;
            }
        }
    }

    /**
     * whether new pin can be added to list
     * @param list
     * @return true if you can continue to add pin,false otherwise
     */
    private boolean addable(List<BowlingTurn> list){
        //assume list is legal
        if (list.size()<getMaxTurn())return true;
        else {
            BowlingTurn lastTurn=list.get(getMaxTurn()-1);//get the last round
            if (!isFinish(lastTurn))return true;
            else if (isSpare(lastTurn)){
                if (list.size()==getMaxTurn()+1)return false;
                else return true;
            }else if (isStrike(lastTurn)){
                if (list.size()==getMaxTurn())return true;
                else if (list.size()==getMaxTurn()+1){
                    BowlingTurn bonusThrow=list.get(getMaxTurn());
                    if (!isFinish(bonusThrow)||isStrike(bonusThrow))return true;
                    else return false;
                }else return false;
            }else return false;
        }

    }
    /**
     * add pin to list,if success return true, otherwise return false.
     * @param list
     * @param pin
     * @return
     */
    private boolean addNewPin(List<BowlingTurn> list,Integer pin){
        BowlingTurn temp=new BowlingTurnImpl(pin);
        if (isValid(temp)){
            list.add(temp);
            return true;
        }
        else return false;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return turn.getFirstPin()==getMaxPin()?true:false;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return !isStrike(turn)&&turn.getFirstPin()+turn.getSecondPin()==getMaxPin();
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        return !isStrike(turn)&&!isMiss(turn);
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        //assume valid
        if (turn.getFirstPin()==getMaxPin())return true;
        else if (turn.getSecondPin()!=null)return true;
        return false;
    }

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        List<BowlingTurn> list=Arrays.asList(allTurns);
        return !addable(list);
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        int length=Math.min(getMaxTurn(),allTurns.length);
        Integer score[]=new Integer[length];
        int bonus[]=new int[length];
        for (int i = 0; i <allTurns.length ; i++) {
            BowlingTurn temp=allTurns[i];
            Integer first=temp.getFirstPin();
            Integer second=temp.getSecondPin();
            if (i<length){
                score[i]=second==null?first:first+second;
                if (i!=allTurns.length-1){
                    if (isStrike(temp))bonus[i]=2;
                    else if (isSpare(temp))bonus[i]=1;
                }
            }
            if (i>0&&i-1<length){
                if (bonus[i-1]>0){
                    score[i-1]+=first;
                    bonus[i-1]--;
                }
                if (bonus[i-1]>0&&second!=null){
                    score[i-1]+=second;
                    bonus[i-1]--;
                }
            }
            if (i>1&&i-2<length){
                if (bonus[i-2]>0){
                    score[i-2]+=first;
                    bonus[i-2]--;
                }
            }
        }
        return score;
    }




    @Override
    public Boolean isValid(BowlingTurn turn) {
        int max=getMaxPin();
        if (turn.getFirstPin()<0||turn.getFirstPin()>max)return false;
        if (turn.getSecondPin()!=null){
            if (turn.getSecondPin()<0||turn.getSecondPin()>max||
                    turn.getFirstPin()+turn.getSecondPin()>max)return false;
        }
        return true;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        if (!isNewPinsAllowed(existingTurns,pins)){
            return existingTurns;
        }
        List<BowlingTurn> list=new ArrayList<>();
        for (BowlingTurn turn :
            existingTurns) {
            list.add(turn);
        }
        for (Integer i :
                pins) {
            addPin(list,i);
        }
        return list.toArray(new BowlingTurn[0]);
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurns;
    }

}
