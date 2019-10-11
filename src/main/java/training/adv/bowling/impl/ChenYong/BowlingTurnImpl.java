package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {
    private Integer first;
    private Integer second;
    private BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
    public BowlingTurnImpl()
    {
        first=null;
        second=null;
    }
    public BowlingTurnImpl(Integer fir,Integer sec)
    {
        first=fir;
        second=sec;
    }

    public BowlingTurnImpl(Integer fir)
    {
        first=fir;
        second=null;
    }



    @Override
    public Integer getFirstPin()
    {
        return first;
    }
    @Override
    public Integer getSecondPin()
    {
        return second;
    }


    @Override
    public BowlingTurnEntity getEntity() {
        bowlingTurnEntity.setFirstPin(first);
        bowlingTurnEntity.setSecondPin(second);
        TurnKey turnKey=new TurnKeyImpl(10,1001);
        bowlingTurnEntity.setId(turnKey);
        return bowlingTurnEntity;
    }
}
