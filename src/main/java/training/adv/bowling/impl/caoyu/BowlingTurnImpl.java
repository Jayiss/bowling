package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity {
    private TurnKey turnKey;
    Integer firstPin, secondPin;

    //constructors
    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        this.setFirstPin(firstPin);
        this.setSecondPin(secondPin);
    }

    public BowlingTurnImpl(Integer firstPin) {
        this.setFirstPin(firstPin);
    }

    public BowlingTurnImpl() {
    }

    //inherited methods
    @Override
    public BowlingTurnEntity getEntity() {
        //to be implemented
        return null;
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
        this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return turnKey;
    }

    @Override
    public void setId(TurnKey turnKey) {
        this.turnKey = turnKey;
    }
}
