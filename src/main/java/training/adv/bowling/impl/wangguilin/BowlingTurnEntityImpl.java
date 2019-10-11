package training.adv.bowling.impl.wangguilin;


import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private TurnKey id;
    private Integer firstPin;
    private Integer secondPin;

    public BowlingTurnEntityImpl(){}

    public BowlingTurnEntityImpl(TurnKey id,Integer first,Integer second){
        this.id = id;
        firstPin = first;
        secondPin = second;
    }

    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id = id;
    }
}
