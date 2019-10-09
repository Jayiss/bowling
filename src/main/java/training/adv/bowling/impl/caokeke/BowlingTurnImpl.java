package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private Integer firstPin;
    private Integer secondPin;

    public BowlingTurnImpl(Integer firstPin,Integer secondPin)
    {
        this.firstPin=firstPin;
        this.secondPin=secondPin;
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
    public BowlingTurnEntity getEntity() {
        return null;
    }
}
