package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

    private Integer first;
    private Integer second;
    private TurnKey key;

    @Override
    public Integer getFirstPin() {
        return first;
    }

    @Override
    public Integer getSecondPin() {
        return second;
    }

    @Override
    public void setFirstPin(Integer pin) {
        first=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        second=pin;
    }

    @Override
    public TurnKey getId() {
        return key;
    }

    @Override
    public void setId(TurnKey id) {
        this.key=id;
    }
}
