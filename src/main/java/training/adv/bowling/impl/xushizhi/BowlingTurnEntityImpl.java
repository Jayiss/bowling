package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

    public static Integer id_turn = 0;  // DB id_turn (TURN PK)
    private TurnKey id_TurnKey;  // DB Turn
    private Integer firstPin, secondPin;

    @Override
    public TurnKey getId() {
        return id_TurnKey;
    }

    @Override
    public void setId(TurnKey id_TurnKey) {
        this.id_TurnKey = id_TurnKey;
    }

    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin = pin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = pin;
    }
}
