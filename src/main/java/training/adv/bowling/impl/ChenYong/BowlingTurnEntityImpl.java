package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

    private Integer firstPin;
    private Integer secondPin;
    private Integer id;
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
        firstPin=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        secondPin=pin;
    }

    @Override
    public TurnKey getId() {
        return new TurnKeyImpl();
    }

    @Override
    public void setId(TurnKey id) {
         this.id=id.getForeignId();
    }
}
