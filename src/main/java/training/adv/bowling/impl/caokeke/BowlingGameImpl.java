package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.*;

public class BowlingGameImpl implements BowlingGame{

    private int MAX_TURNS;
    private int MAX_SCORE;
    private int curTurn;
    private BowlingRule br;
    private Integer [] scores;
    private BowlingTurn [] turns;
    private BowlingGameEntity bge;


    public BowlingGameImpl()
    {
        curTurn=0;

        br=new BowlingRuleImpl();

        MAX_SCORE=br.getMaxPin();
        MAX_TURNS=br.getMaxTurn();

        scores=new Integer[MAX_TURNS+3];
        turns=new BowlingTurn[MAX_TURNS+3];

        bge=new BowlingGameEntityImpl();
    }
    public BowlingGameImpl(Integer curTurn,BowlingTurn []turns,BowlingGameEntity bge)
    {
        this.curTurn=curTurn;

        br=new BowlingRuleImpl();

        MAX_SCORE=br.getMaxPin();
        MAX_TURNS=br.getMaxTurn();

        scores=new Integer[MAX_TURNS+3];
        this.turns=turns;
        this.bge=bge;
        Integer[] tmp=getScores();
        for(int i=0;i<tmp.length;i++)
            scores[i+1]=tmp[i];
    }

    @Override
    public BowlingGameEntity getEntity() {
        return bge;
    }

    @Override
    public Integer getTotalScore()
    {
        Integer res=0;
        int maxTurn=curTurn>MAX_TURNS?MAX_TURNS:curTurn;
        for(int i=1;i<=maxTurn;i++)
            res+=scores[i];
        return res;
    }

    //the score of current turn
    @Override
    public Integer[] getScores()
    {
        if(curTurn<1)
            return null;
        return br.calcScores(getTurns());
    }

    @Override
    public BowlingTurn[] getTurns()
    {
        if(curTurn<1)
            return null;
        BowlingTurn []res=new BowlingTurn[curTurn];
        for(int i=1;i<=curTurn;i++)
            res[i-1]=turns[i];
        return res;
    }

    @Override
    public Integer[] addScores(Integer... pins)
    {
        if(pins.length==0 || !br.isNewPinsAllowed(getTurns(),pins))
            return getScores();

        int index=0;

        if(turns[curTurn]!=null && turns[curTurn].getSecondPin()==null && turns[curTurn].getFirstPin()!=MAX_SCORE)
            turns[curTurn]=new BowlingTurnImpl(turns[curTurn].getFirstPin(),pins[index++],index,bge.getId());

        while(index<pins.length){
            if(pins[index]==MAX_SCORE)
                turns[++curTurn]=new BowlingTurnImpl(pins[index++],0,index,bge.getId());
            else if(pins[index]<MAX_SCORE && pins[index]>0 && index+1>=pins.length)
                turns[++curTurn]=new BowlingTurnImpl(pins[index++],null,index,bge.getId());
            else if(pins[index]<MAX_SCORE && pins[index]>0 && pins[index+1] >0 && pins[index+1]+pins[index]<=MAX_SCORE)
                turns[++curTurn]=new BowlingTurnImpl(pins[index++],pins[index++],index,bge.getId());
            else{
                System.out.println("invalid data");
                break;
            }
        }

        Integer[] tmp=getScores();
        for(int i=0;i<tmp.length;i++)
            scores[i+1]=tmp[i];
        return tmp;
    }
}
