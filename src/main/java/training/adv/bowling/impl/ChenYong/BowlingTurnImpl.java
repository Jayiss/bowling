package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {
    private Integer first;
    private Integer second;
    private BowlingTurnEntity bowlingTurnEntity;



    public BowlingTurnImpl(Integer fir,Integer sec,TurnKey turnKey)
    {
        first=fir;
        second=sec;
        bowlingTurnEntity=new BowlingTurnEntityImpl(fir,sec,turnKey);
    }

    public BowlingTurnImpl(Integer fir,TurnKey turnKey)
    {
        first=fir;
        second=null;
        bowlingTurnEntity=new BowlingTurnEntityImpl(fir,null,turnKey);
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
        BowlingTurnEntity bowlingTurnEntity1=new BowlingTurnEntityImpl(bowlingTurnEntity.getFirstPin(),bowlingTurnEntity.getSecondPin(),bowlingTurnEntity.getId());
        return bowlingTurnEntity1;
    }
}
