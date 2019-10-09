package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private int firstPin;
    private int secondPin;
    private TurnKey turnKey;


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
        return turnKey;
    }

    @Override
    public void setId(TurnKey id) {
        turnKey = id;
    }

}
