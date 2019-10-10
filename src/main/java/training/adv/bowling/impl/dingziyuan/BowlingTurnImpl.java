package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity {
    private Integer[] pins = new Integer[2];
    TurnKey id;
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
    public void setFirstPin(Integer pin) {
        pins[0] = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        pins[1] = pin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this;
    }

    @Override
    public TurnKey getId() {
        return id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id = id;
    }
}
