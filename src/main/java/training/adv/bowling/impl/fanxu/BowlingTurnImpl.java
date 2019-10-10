package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn{
    private Integer firstPin;
    private Integer secondPin;
    BowlingTurnImpl(Integer firstPin,Integer secondPin){
        this.firstPin = firstPin;
        this.secondPin = secondPin;
    }
    @Override
    public Integer getFirstPin() {
        return this.firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return this.secondPin;
    }


    @Override
    public BowlingTurnEntity getEntity() {
        BowlingTurnEntity bowlingTurnEntity =  new BowlingTurnInfo();
        bowlingTurnEntity.setFirstPin(firstPin);
        bowlingTurnEntity.setSecondPin(secondPin);
        return bowlingTurnEntity;
    }
}
