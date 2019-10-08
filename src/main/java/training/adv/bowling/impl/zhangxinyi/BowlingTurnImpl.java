package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {
    private Integer firstPin = null;
    private Integer secondPin = null;

    public BowlingTurnImpl(Integer... pins) {
        if (pins.length == 1) {
            firstPin = pins[0];
        } else if (pins.length == 2) {
            firstPin = pins[0];
            secondPin = pins[1];
        }
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
