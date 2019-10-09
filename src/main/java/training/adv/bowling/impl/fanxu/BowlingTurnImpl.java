package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity {
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey turnKeyId;

    BowlingTurnImpl(){

    }
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
    public void setFirstPin(Integer pin) {
        this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin =  pin;
    }

    @Override
    public TurnKey getId() {
        return this.turnKeyId;
    }

    @Override
    public void setId(TurnKey id) {
        this.turnKeyId = id;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return null;
    }
}
