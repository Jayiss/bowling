package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class BowlingRuleImpl implements BowlingRule {
    public static Integer MAX_PINS=10;
    public static Integer MAX_TURN=10;
    private Integer foreignId;
    public BowlingRuleImpl()
    {
        int gameId=1001;
        String sql="select max(id) from game";
        try{
            PreparedStatement ps= DBUtil.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>1000)
                gameId=rs.getInt(1)+1;

        }catch (Exception e){}
        this.foreignId=gameId;

    }
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins)
    {
        List<Integer> list_pins=new ArrayList<>();
        for(int i=0;i<existingTurns.length;i++)
        {
            list_pins.add(existingTurns[i].getFirstPin());
            if(existingTurns[i].getSecondPin()!=null&&!isStrike(existingTurns[i]))
                list_pins.add(existingTurns[i].getSecondPin());
        }
        for (Integer pin:
                newPins) {list_pins.add(pin);
        }
        return check(list_pins);
    }

    public Boolean isStrike(BowlingTurn turn)
    {
        if(turn.getFirstPin()==getMaxTurn())
            return true;
        return false;
    }

    public Boolean isSpare(BowlingTurn turn){
        if(!isFinish(turn))
            return false;
        if(turn.getFirstPin()<10&&turn.getFirstPin()+turn.getSecondPin()==getMaxPin())
            return true;
        return false;
    }
    public Boolean isMiss(BowlingTurn turn)
    {
        if(turn.getFirstPin()==0&&turn.getSecondPin()==0)
            return true;
        return false;
    }

    public Boolean isFinish(BowlingTurn turn)
    {
        if(turn.getFirstPin()<10&&turn.getSecondPin()==null)
            return false;
        return true;
    }

    public Integer getMaxPin()
    {
        return MAX_PINS;
    }
    public Integer getMaxTurn()
    {
        return MAX_TURN;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        if(turn.getFirstPin()<0&&turn.getFirstPin()>10)
            return false;
        if(turn.getSecondPin()<0&&turn.getSecondPin()>10)
            return false;
        if(turn.getFirstPin()+turn.getSecondPin()>10)
            return false;
        return true;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        if(allTurns.length<MAX_TURN)
            return false;
        if(allTurns.length==getMaxTurn())
        {
            int temp=getMaxTurn();
            if(!isFinish(allTurns[temp-1]))
                return false;
            else if(isMiss(allTurns[temp-1]))
            {
                return true;
            }
            else if(temp+1==allTurns.length)
            {
                if(isStrike(allTurns[temp-1])&&(isMiss(allTurns[temp])||isSpare(allTurns[temp])))
                    return true;
                if(isSpare(allTurns[temp-1])&&(!isFinish(allTurns[temp])||isStrike(allTurns[temp])))
                    return true;
            }
            else if(temp+2==allTurns.length)
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        Integer[] eachTurnScore=new Integer[MAX_TURN];

        for(int i=0;i<10;i++)
            eachTurnScore[i]=0;
        for(int i=0;i<(allTurns.length<MAX_TURN?allTurns.length:MAX_TURN);i++)
        {
            if(!isFinish(allTurns[i]))
                eachTurnScore[i]+=allTurns[i].getFirstPin();
            else
                eachTurnScore[i]+=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
        }

        int turnCount=allTurns.length<MAX_TURN?allTurns.length:MAX_TURN;
        for(int i=0;i<turnCount;i++)
        {
            if(isSpare(allTurns[i]))
            {
                if(allTurns.length>i+1)
                    eachTurnScore[i]+=allTurns[i+1].getFirstPin();
            }
            else if(isStrike(allTurns[i]))
            {
                if(allTurns.length==i+2)
                {
                    if(!isFinish(allTurns[i+1]))
                        eachTurnScore[i]+=allTurns[i+1].getFirstPin();
                    else
                        eachTurnScore[i]+=allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin();
                }

                else if(allTurns.length>i+2)
                {
                    if(isStrike(allTurns[i+1]))
                        eachTurnScore[i]+=allTurns[i+1].getFirstPin()+allTurns[i+2].getFirstPin();
                    else
                        eachTurnScore[i]+=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
                }
            }
        }

        return eachTurnScore;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        List<BowlingTurn> listTurns=new ArrayList<>();
        if(!isNewPinsAllowed(existingTurns,pins))
            return existingTurns;

        if(existingTurns.length!=0)
        {
            for (BowlingTurn turn:existingTurns
            ) {
                listTurns.add(turn);
            }
        }

        for(int i=0;i<pins.length;i++)
        {
            int listTurnSize=listTurns.size();
            if(listTurnSize==0)
            {
                if(pins[0]<MAX_PINS) {
                    Integer id=listTurns.size();
                    TurnKey turnKey=new TurnKeyImpl(id,this.foreignId);
                    listTurns.add(new BowlingTurnImpl(pins[0],turnKey));
                }
                else {
                    Integer id=listTurns.size();
                    TurnKey turnKey=new TurnKeyImpl(id,this.foreignId);
                    listTurns.add(new BowlingTurnImpl(pins[0], 0,turnKey));
                }
                continue;
            }

            if(!isFinish(listTurns.get(listTurnSize-1)))
            {
                Integer first=listTurns.get(listTurnSize-1).getFirstPin();
                listTurns.remove(listTurnSize-1);

                Integer id=listTurns.size();
                TurnKey turnKey=new TurnKeyImpl(id,this.foreignId);
                BowlingTurnImpl newTurn=new BowlingTurnImpl(first,pins[i],turnKey);
                listTurns.add(newTurn);
                continue;
            }


            if(pins[i]==MAX_PINS)
            {
                Integer id=listTurns.size();
                TurnKey turnKey=new TurnKeyImpl(id,this.foreignId);
                BowlingTurnImpl newTurn=new BowlingTurnImpl(MAX_PINS,0,turnKey);
                listTurns.add(newTurn);
                continue;
            }
            Integer id=listTurns.size();
            TurnKey turnKey=new TurnKeyImpl(id,this.foreignId);
            BowlingTurnImpl newTurn=new BowlingTurnImpl(pins[i],turnKey);
            listTurns.add(newTurn);
        }
        return listTurns.toArray(new BowlingTurn[0]);
    }

    //if input wrong, return false,else return true
    public boolean check(List<Integer> list_pins)
    {
        int tempCountTurn=0;
        boolean tempFlag=true;
        int someTurnScore=0;
        for(int i=0;i<list_pins.size();i++)
        {
            if(list_pins.get(i)<0||list_pins.get(i)>MAX_PINS)
                return false;
            if(list_pins.get(i)==MAX_PINS) {
                someTurnScore=0;
                tempCountTurn++;
            }
            else if(tempFlag)
            {
                tempCountTurn++;
                someTurnScore=list_pins.get(i);
                tempFlag=false;
            }
            else
            {
                someTurnScore+=list_pins.get(i);
                tempFlag=true;
            }
            if(someTurnScore>10)
                return false;

            if(tempCountTurn==10&&someTurnScore==10&&tempFlag)  //the 10th is spare
                if(list_pins.size()-i>2)
                    return false;
            if(tempCountTurn==10&&someTurnScore==10)  //the 10th is strike
            {
                if(!tempFlag&&(list_pins.size()-i>3))
                    return false;
            }
            if(tempCountTurn==10&&someTurnScore==0)
            {
                if(list_pins.size()-i>3)
                    return false;
            }
        }

        return true;
    }
}
