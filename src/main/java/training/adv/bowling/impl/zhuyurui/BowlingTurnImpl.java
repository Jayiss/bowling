package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();

    public BowlingTurnImpl(){

    }

    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        this.bowlingTurnEntity.setFirstPin(firstPin);
        this.bowlingTurnEntity.setSecondPin(secondPin);
    }

    public BowlingTurnImpl(Integer firstPin) {
        this(firstPin, null);
    }

    public BowlingTurnImpl(BowlingTurnEntity entity) {
        this(entity.getFirstPin(),entity.getSecondPin());
        this.bowlingTurnEntity.setId(entity.getId());
    }

    @Override
    public Integer getFirstPin() {

        return bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {

        return bowlingTurnEntity.getSecondPin();
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return bowlingTurnEntity;
    }
}
