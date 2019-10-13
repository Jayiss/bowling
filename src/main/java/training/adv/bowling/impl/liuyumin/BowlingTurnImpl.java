package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.util.ArrayList;
import java.util.List;

public class BowlingTurnImpl implements BowlingTurn {
    private int gameId;
    private int turnId;
    private List<Integer> pins = new ArrayList<>();

    public BowlingTurnImpl () {}

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
        BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
        TurnKeyImpl turnKey = new TurnKeyImpl(turnId, gameId);
        bowlingTurnEntity.setId(turnKey);
        bowlingTurnEntity.setFirstPin(pins.get(0));
        if (pins.size() == 2) {
            bowlingTurnEntity.setSecondPin(pins.get(1));
        }
        return bowlingTurnEntity;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public int getTurnId() {
        return this.turnId;
    }
}
