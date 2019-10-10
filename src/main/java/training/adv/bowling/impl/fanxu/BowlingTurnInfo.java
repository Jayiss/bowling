package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnInfo implements BowlingTurnEntity {
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey turnKey;
    BowlingTurnInfo(){

    }
    BowlingTurnInfo(Integer firstPin,Integer secondPin,TurnKey turnKey){
        this.firstPin = firstPin;
        this.secondPin = secondPin;
        this.turnKey = turnKey;
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
        this.firstPin = firstPin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = secondPin;
    }

    @Override
    public TurnKey getId() {
        return turnKey;
    }

    @Override
    public void setId(TurnKey id) {
        this.turnKey = id;
    }
}
