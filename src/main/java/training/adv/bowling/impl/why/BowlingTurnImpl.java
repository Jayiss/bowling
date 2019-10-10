package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {


    private BowlingTurnEntity entity;

    public BowlingTurnImpl(Integer first, Integer second){
        this(first);
        entity.setSecondPin(second);
    }

    public BowlingTurnImpl(Integer first){
        entity=new BowlingTurnEntityImpl();
        entity.setFirstPin(first);
    }

    BowlingTurnImpl(BowlingTurnEntity entity){
        this.entity=entity;
    }

    void setSecond(int second){
        entity.setSecondPin(second);
    }

    void setKey(TurnKey key){
        entity.setId(key);
    }

    @Override
    public Integer getFirstPin() {
        return entity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return entity.getSecondPin();
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return entity;
    }
}
