package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.util.ArrayList;
import java.util.List;

public class BowlingTurnImpl implements BowlingTurn {

    private List<Integer> pins = new ArrayList<>();

    @Override
    public Integer getFirstPin() {
        return pins.get(0);
    }

    @Override
    public Integer getSecondPin() {
        if(pins.size()==2){
            return pins.get(1);
        } else {
            return 0;
        }
    }

    public void setFirstPin(int num) {
        pins.add(num);
    }

    public void setSecondPin(int num) {
        pins.add(num);
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return null;
    }
}
