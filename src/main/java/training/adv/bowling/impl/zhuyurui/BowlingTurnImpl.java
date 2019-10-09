package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {

    private Integer firstpin;
    private Integer secondpin;

    public BowlingTurnImpl(Integer firstpin){
        this.firstpin=firstpin;
        this.secondpin=null;
    }

    public BowlingTurnImpl(int firstpin,int secondpin){
        this.firstpin=firstpin;
        this.secondpin=secondpin;
    }

    @Override
    public Integer getFirstPin() {

        return firstpin;
    }

    @Override
    public Integer getSecondPin() {

        return secondpin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
        bowlingTurnEntity.setFirstPin(firstpin);
        bowlingTurnEntity.setSecondPin(secondpin);
        return bowlingTurnEntity;
    }
}
