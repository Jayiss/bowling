package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

import java.util.IdentityHashMap;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

    private Integer FirstPin;
    private  Integer SecondPin;
    private  TurnKey Id; //{ID,BOWLINGGAMEID}

    @Override
    public Integer getFirstPin() {
        return FirstPin;
    }

    @Override
    public void setFirstPin(Integer firstPin) {
        FirstPin = firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return SecondPin;
    }

    @Override
    public void setSecondPin(Integer secondPin) {
        SecondPin = secondPin;
    }


    @Override
    public TurnKey getId() {
        return this.Id;
    }

    @Override
    public void setId(TurnKey id) {
        this.Id = id;
    }
}
