package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    public static Integer uniqueId = 0;

    private TurnKey id;
    private Integer firstPin;
    private Integer secondPin;
    private Integer foreignId;

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
        firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id = id;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer id) {
        foreignId = id;
    }
}
