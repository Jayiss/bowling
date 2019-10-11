package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private BowlingTurnEntity entity = new BowlingTurnEntityImpl();

    BowlingTurnImpl(Integer... pins) {
        if (pins.length == 1) {
            entity.setFirstPin(pins[0]);
            entity.setSecondPin(null);
        } else if (pins.length == 2) {
            entity.setFirstPin(pins[0]);
            entity.setSecondPin(pins[1]);
        }
    }

    @Override
    public Integer getFirstPin() {
        return entity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return entity.getSecondPin();
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return entity;
    }
}