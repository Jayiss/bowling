package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {
    private Integer firstPin,secondPin;

    //constructors
    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        this.firstPin = firstPin;
        this.secondPin = secondPin;
    }

    public BowlingTurnImpl(Integer firstPin) {
        this.firstPin = firstPin;
    }

    public BowlingTurnImpl() {
    }

    //inherited methods
    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        //to be implemented
        return null;
    }
}
