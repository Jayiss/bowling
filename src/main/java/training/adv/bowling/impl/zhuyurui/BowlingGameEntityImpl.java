package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

import java.time.Instant;

public class BowlingGameEntityImpl implements GameEntity {

    private TurnEntity[] turnEntities;
    private int id;

    @Override
    public void setTurnEntities(TurnEntity[] turns) {
        turnEntities = turns;
    }

    @Override
    public TurnEntity[] getTurnEntities() {

        return turnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return BowlingRuleImpl.getInstance().getMaxTurn();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
