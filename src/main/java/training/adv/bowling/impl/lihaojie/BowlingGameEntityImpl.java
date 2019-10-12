package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private  int id;
    private BowlingTurnEntity[]turnEntities;
    private int maxTurn;
    @Override
    public Integer getMaxPin() {
        return null;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        turnEntities=turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
