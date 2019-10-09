package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private Integer firstPin;
    private Integer secondPin;



    public BowlingTurnImpl(Integer firstPin, Integer secondPin){
        this.firstPin = firstPin;
        this.secondPin = secondPin;
    }

    public BowlingTurnImpl(Integer firstPin){
        this(firstPin,null);
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
    public BowlingTurnEntity getEntity() {
        BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
        bowlingTurnEntity.setFirstPin(firstPin);
        bowlingTurnEntity.setSecondPin(secondPin);
        return bowlingTurnEntity;
    }
}
