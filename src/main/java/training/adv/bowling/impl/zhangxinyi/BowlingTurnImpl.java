package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {
    private BowlingTurnEntity turn = new BowlingTurnEntityImpl();

    // Stored Turn[] should be either legal or not finished.
    public BowlingTurnImpl(Integer... pins) {
        if (pins.length == 1) {
            getEntity().setFirstPin(pins[0]);
            getEntity().setSecondPin(null);
        } else if (pins.length == 2) {
            getEntity().setFirstPin(pins[0]);
            getEntity().setSecondPin(pins[0]);
        }
    }

    @Override
    public Integer getFirstPin() {
        return getEntity().getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return getEntity().getSecondPin();
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return turn;
    }
}
