package training.adv.bowling.impl.zhangpeng;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.pengzhang.TurnKeyImpl;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity {
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey bowlingTurnId;

    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        this.firstPin = firstPin;
        this.secondPin = secondPin;
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
        if (firstPin == 0)
            this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        if (firstPin != 10)
            this.secondPin = pin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
//        BowlingTurnEntity copyOfTurnEntity = new BowlingTurnImpl(this.firstPin, this.secondPin);
//        copyOfTurnEntity.setId(bowlingTurnId);
        return this;
    }

    @Override
    public TurnKey getId() {
        return new TurnKeyImpl(bowlingTurnId.getId(), bowlingTurnId.getForeignId());
//        return bowlingTurnId;
    }

    @Override
    public void setId(TurnKey id) {
        if (bowlingTurnId == null)
            this.bowlingTurnId = id;
    }
}