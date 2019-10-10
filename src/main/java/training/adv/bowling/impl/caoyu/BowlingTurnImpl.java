package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {
    private BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();

    //constructors
    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        bowlingTurnEntity.setFirstPin(firstPin);
        bowlingTurnEntity.setSecondPin(secondPin);
    }

    public BowlingTurnImpl(Integer firstPin) {
        bowlingTurnEntity.setFirstPin(firstPin);
    }

    public BowlingTurnImpl() {
    }

    //inherited methods
    @Override
    public Integer getFirstPin() {
        return bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return bowlingTurnEntity.getSecondPin();
    }

    @Override
    public BowlingTurnEntity getEntity() {
        //to be implemented
        return null;
    }
}
