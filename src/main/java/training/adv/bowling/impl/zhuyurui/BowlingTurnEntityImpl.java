package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private int firstpin;
    private int secondpin;
    private TurnKey turnKey;



    @Override
    public Integer getFirstPin() {

        return firstpin;
    }

    @Override
    public Integer getSecondPin() {

        return secondpin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        firstpin=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        secondpin=pin;
    }

    @Override
    public TurnKey getId() {
        return turnKey;
    }

    @Override
    public void setId(TurnKey id) {

        turnKey=id;
    }

}
