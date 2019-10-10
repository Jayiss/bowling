package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.io.Serializable;

public class BowlingTurnImpl implements BowlingTurn, Serializable {
    private int []pins=new int[2];
    private int length=0;
    public BowlingTurnImpl(int... pins){
        for (int i = 0; i < 2; i++) {
            this.pins[i]=-1;
        }
        for (int i = 0; i < pins.length; i++) {
            this.pins[i]=pins[i];
            length++;
        }
    }
    @Override
    public Integer getFirstPin() {
        return pins[0];
    }

    @Override
    public Integer getSecondPin() {
        return pins[1];
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return null;
    }

    public int getLength() {
        return length;
    }
    public void setSecondPin(int i){
        pins[1]=i;
    }
}
