package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {
    private Integer[] pins = new Integer[2];
    public Integer len = 0;

    public BowlingTurnImpl(Integer... pins) {
        this.pins[0] = pins[0];
        if (pins.length == 2) {
            this.pins[1] = pins[1];
        }
        this.len = pins.length;
    }

    @Override
    public Integer getFirstPin() {
        return pins[0];
    }

    @Override
    public Integer getSecondPin() {
        return pins[1];
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return null;
    }
}
