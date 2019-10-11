package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

import java.io.Serializable;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private Integer MaxPin = 10;
    private Integer MaxTurn = 10;
    private BowlingTurnEntity[] turnEntities;
    private Integer Id;

    public void setMaxPin(Integer maxPin) {
        this.MaxPin = maxPin;
    }

    @Override
    public Integer getMaxPin() {
        return this.MaxPin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.turnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return this.turnEntities;
    }

    public void setMaxTurn(Integer maxTurn) {
        this.MaxTurn = maxTurn;
    }

    @Override
    public Integer getMaxTurn() {
        return this.MaxTurn;
    }

    @Override
    public Integer getId() {
        return this.Id;
    }

    @Override
    public void setId(Integer id) {
        this.Id = id;
    }
}
