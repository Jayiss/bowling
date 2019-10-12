package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

import java.io.Serializable;

public class BowlingTurnImpl implements BowlingTurn, Serializable {
    private int []pins=new int[2];
    private int length=0;
    private int turn_Id;
    private int foreign_id;
    static int turnId=0;
    public BowlingTurnImpl(int... pins){
        for (int i = 0; i < 2; i++) {
            this.pins[i]=-1;
        }
        for (int i = 0; i < pins.length; i++) {
            this.pins[i]=pins[i];
            length++;
        }
    }
    public BowlingTurnImpl(){

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
        BowlingTurnEntity bowlingTurnEntiy=new BowlingTurnEntityImpl();
        bowlingTurnEntiy.setSecondPin(pins[1]);
        bowlingTurnEntiy.setFirstPin(pins[0]);
        bowlingTurnEntiy.setId(new TurnKeyImpl(this.turn_Id,this.foreign_id));
        return bowlingTurnEntiy;
    }

    public int getLength() {
        return length;
    }
    public void setFirstPin(int i){pins[0]=i;}
    public void setSecondPin(int i){
        pins[1]=i;
    }

    public void setForeign_id(int foreign_id) {
        this.foreign_id = foreign_id;
    }

    public void setTurn_Id(int turn_Id) {
        this.turn_Id = turn_Id;
    }
}
