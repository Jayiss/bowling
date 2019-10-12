package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();

    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {  // Accept null
        getEntity().setFirstPin(firstPin);
        getEntity().setSecondPin(secondPin);
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return turnEntity;
    }

    @Override
    public Integer getFirstPin() {
        return getEntity().getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return getEntity().getSecondPin();
    }
}
