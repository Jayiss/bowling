package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {

    private Integer first;
    private Integer second;
    private TurnKey key;

    public BowlingTurnImpl(int first, int second){
        this.first=first;
        this.second=second;
    }

    public BowlingTurnImpl(int first){
        this.first=first;
    }

    void setSecond(int second){
        this.second=second;
    }

    void setKey(TurnKey key){
        this.key=key;
    }

    @Override
    public Integer getFirstPin() {
        return first;
    }

    @Override
    public Integer getSecondPin() {
        return second;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        BowlingTurnEntityImpl temp=new BowlingTurnEntityImpl();
        temp.setFirstPin(first);
        temp.setSecondPin(second);
        temp.setId(key);
        return temp;
    }
}
