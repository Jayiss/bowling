package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.Turn;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private TurnKey id;
    private int firstPin;
    private int secondPin;
    @Override
    public Integer getFirstPin() {
        return this.firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return this.secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin=pin;
    }

    @Override
    public TurnKey getId() {
        return id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id=id;
    }
}
