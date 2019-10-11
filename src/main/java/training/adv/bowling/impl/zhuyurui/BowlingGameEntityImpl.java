package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.*;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private BowlingTurnEntity[] turnEntities;
    private Integer id;

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {

        turnEntities = turns;
    }


    @Override
    public BowlingTurnEntity[] getTurnEntities() {

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

    @Override
    public Integer getMaxPin() {
        return BowlingRuleImpl.getInstance().getMaxPin();
    }
}
