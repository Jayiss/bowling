package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private TurnKey turnId;
    private int firstPin;
    private int secondPin;
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
        return this.turnId;
    }

    @Override
    public void setId(TurnKey id) {
        this.turnId = new TurnKeyImpl(id.getId(), id.getForeignId());
    }
}
