package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private int firstPin;
    private int secondPin;
    private TurnKey ID;

    public BowlingTurnEntityImpl(BowlingTurn bowlingTurn){
        setFirstPin(bowlingTurn.getFirstPin());
        setSecondPin(bowlingTurn.getSecondPin());
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
    public void setFirstPin(Integer pin) {
        this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return ID;
    }

    @Override
    public void setId(TurnKey id) {
        if(id.getId() > 0 && id.getForeignId() > 0) this.ID = id;
    }
}
