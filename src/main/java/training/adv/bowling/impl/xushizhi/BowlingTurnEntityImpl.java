package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private TurnKey id;  // DB Turn PK
    private Integer firstPin, secondPin;

    @Override
    public TurnKey getId() {
        return id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id = id;
    }

    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin = pin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = pin;
    }
}
