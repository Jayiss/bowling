package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

public class BowlingRuleImpl implements BowlingRule {

    private static final int MAX_SCORE=10;
    private static final int MAX_TURNS=10;

    private boolean isDataValid(Integer n){return n>=0 && n<=MAX_SCORE;}

    private boolean judgeAux(BowlingTurn [] aux,int curLen,Integer[] newPins)
    {
        for(int i=0;i<newPins.length;i++)if(!isDataValid(newPins[i]))return false;
        int index=0;
        while(index<newPins.length){
            if(newPins[index]<0 ||
                    newPins[index]>MAX_SCORE ||
                    (newPins[index]!=MAX_SCORE && index+1<newPins.length && newPins[index]+newPins[index+1]>MAX_SCORE))
                return false;
            index+=2;
        }

        index=0;
        if(curLen>0&&aux[curLen].getSecondPin()==null)
            aux[curLen]=new BowlingTurnImpl(aux[curLen].getFirstPin(),newPins[index++]);

        while(index<newPins.length){
            if(curLen>=MAX_TURNS)break;
            if(newPins[index]==MAX_SCORE)
                aux[++curLen]=new BowlingTurnImpl(newPins[index++],0);
            else if(newPins[index]<MAX_SCORE && newPins[index]>0 && index+1>=newPins.length)
                aux[++curLen]=new BowlingTurnImpl(newPins[index++],null);
            else if(newPins[index]<MAX_SCORE && newPins[index]>0 && newPins[index+1] >0 && newPins[index+1]+newPins[index]<=MAX_SCORE)
                aux[++curLen]=new BowlingTurnImpl(newPins[index++],newPins[index++]);
        }
        if(index<newPins.length){
            if(newPins.length-index>2)return false;
            if(newPins.length-index==1 && !isSpare(aux[MAX_TURNS]))return false;
            if(newPins.length-index==2 && !isStrike(aux[MAX_TURNS]))return false;
        }
        return true;
    }
    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins)
    {
        if(newPins.length==0)
            return true;

        BowlingTurn [] aux=new BowlingTurnImpl[MAX_TURNS+3];
        int curLen=existingTurns==null?0:existingTurns.length;
        if(curLen>0)for(int i=1;i<curLen+1;i++)aux[i]=existingTurns[i-1];
        return judgeAux(aux,curLen,newPins);
    }

    @Override
    public Boolean isStrike(BowlingTurn turn)
    {
        return turn.getFirstPin()==MAX_SCORE;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn)
    {
        if(turn.getSecondPin()==null)
            return false;
        return turn.getFirstPin()+turn.getSecondPin()==MAX_SCORE;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn)
    {
        return turn.getSecondPin()+turn.getFirstPin()<MAX_SCORE;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn)
    {
        return turn.getSecondPin()==null;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_SCORE;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns)
    {
        if(allTurns.length<MAX_TURNS || !isFinish(allTurns[allTurns.length-1]))
            return false;

        Integer lastPin1=allTurns[MAX_TURNS-1].getFirstPin();
        Integer lastPin2=allTurns[MAX_TURNS-1].getSecondPin();


        return (lastPin1==MAX_SCORE && allTurns.length==MAX_TURNS+2) ||
                (lastPin1<MAX_SCORE && lastPin1+lastPin2==MAX_SCORE && allTurns.length==MAX_TURNS+1) ||
                (lastPin1+lastPin2<10 && allTurns.length==MAX_TURNS);
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        if(allTurns.length==0)
            return null;


        Integer [] res;
        if(allTurns.length>=MAX_TURNS)
            res=new Integer[MAX_TURNS];
        else
            res=new Integer[allTurns.length];

        int i;
        for(i=0;i<res.length;i++){
            if(isValid(allTurns[i])){
                res[i]=allTurns[i].getFirstPin();
                if(allTurns[i].getSecondPin()!=null)res[i]+=allTurns[i].getSecondPin();
                if(i-1>=0 && isSpare(allTurns[i-1]))
                    res[i-1]+=allTurns[i].getFirstPin();

                if(i-1>=0 && allTurns[i].getSecondPin()!=null && allTurns[i].getSecondPin()>0 && isStrike(allTurns[i-1]))
                    res[i-1]+=allTurns[i].getSecondPin();

                if(i-2>=0 && allTurns[i-1].getSecondPin()!=null && allTurns[i-1].getSecondPin()==0 && isStrike(allTurns[i-2]))
                    res[i-2]+=allTurns[i].getFirstPin();
            }
            else{
                System.out.println("invalid");
                break;
            }
        }

        if(i<allTurns.length){
            if(i-1>=0 && isSpare(allTurns[i-1]))
                res[i-1]+=allTurns[i].getFirstPin();
            if(i-1>=0 && allTurns[i].getSecondPin()!=null && allTurns[i].getSecondPin()>0 && isStrike(allTurns[i-1]))
                res[i-1]+=allTurns[i].getSecondPin();
            if(i-2>=0 && allTurns[i-1].getSecondPin()==0 && isStrike(allTurns[i-2]))
                res[i-2]+=allTurns[i].getFirstPin();
            if(i+1<allTurns.length && allTurns[i-1].getSecondPin()==0 && isStrike(allTurns[i-1]))
                res[i-1]+=allTurns[i+1].getFirstPin();
        }

        return res;
    }

    @Override
    public Boolean isValid(BowlingTurn turn)
    {
        if(turn.getSecondPin()==null)
            return turn.getFirstPin()>=0 &&turn.getFirstPin()<=MAX_SCORE;

        return turn.getFirstPin()>=0 && turn.getSecondPin()>=0 &&
                turn.getFirstPin()<=MAX_SCORE && turn.getSecondPin()<=MAX_SCORE &&
                turn.getFirstPin()+turn.getSecondPin()<=MAX_SCORE;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins)
    {
        if(pins.length==0 || !isNewPinsAllowed(existingTurns,pins))
            return existingTurns;
        BowlingTurn []turns=new BowlingTurn[MAX_TURNS+3];

        int curTurn=existingTurns.length;

        int index=0;

        //复制一份
        for(int i=1;i<=curTurn;i++)turns[i]=existingTurns[i];

        if(turns[curTurn]!=null&&existingTurns[curTurn].getSecondPin()==null)
            turns[curTurn]=new BowlingTurnImpl(existingTurns[curTurn-1].getFirstPin(),pins[index++]);

        while(index<pins.length){
            if(curTurn>MAX_TURNS)break;
            if(pins[index]==MAX_SCORE)
                turns[++curTurn]=new BowlingTurnImpl(pins[index++],0);
            else if(pins[index]<MAX_SCORE && pins[index]>0 && index+1>=pins.length)
                turns[++curTurn]=new BowlingTurnImpl(pins[index++],null);
            else if(pins[index]<MAX_SCORE && pins[index]>0 && pins[index+1] >0 && pins[index+1]+pins[index]<=MAX_SCORE)
                turns[++curTurn]=new BowlingTurnImpl(pins[index++],pins[index++]);
            else{
                System.out.println("invalid data");
                break;
            }
        }
        if(index<pins.length){
            if(pins.length-index>2)return existingTurns;
            else if(pins.length-index==1){
                if(isSpare(turns[MAX_TURNS])){
                    turns[curTurn]=new BowlingTurnImpl(pins[index],0);
                    return turns;
                }
                else return existingTurns;
            }
            else{
                if(!isStrike(turns[MAX_TURNS]))return existingTurns;
                if(pins[index]==10){
                    turns[curTurn]=new BowlingTurnImpl(pins[index],0);
                    turns[++curTurn]=new BowlingTurnImpl(pins[++index],0);
                    return turns;
                }
                turns[curTurn]=new BowlingTurnImpl(pins[index++],pins[index]);
                return turns;
            }
        }
        return turns;
    }

    @Override
    public Integer getMaxTurn()
    {
        return MAX_TURNS;
    }
}
