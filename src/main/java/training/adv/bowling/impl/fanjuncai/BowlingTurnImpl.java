package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private Integer numOfPins;
    private Integer FirstPin;
    private Integer SecondPin;

    public BowlingTurnImpl(Integer[] turn) {
        //this.blTurn = turn;
        this.numOfPins = turn.length;
    }

    public Integer getNumOfPins() {
        return numOfPins;
    }

    public void setNumOfPins(Integer numOfPins) {
        this.numOfPins = numOfPins;
    }

    public void setFirstPin(Integer firstPin) {
        FirstPin = firstPin;
    }

    public void setSecondPin(Integer secondPin) {
        SecondPin = secondPin;
    }

    public BowlingTurnImpl(){
        this.numOfPins = null;
        this.FirstPin = null;
        this.SecondPin = null;
    }

    @Override
    public Integer getFirstPin() {
        return this.FirstPin;
    }

    @Override
    public Integer getSecondPin() {
        return this.SecondPin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return null;
    }
}
