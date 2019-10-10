package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;


public class BowlingGameEntityImpl implements BowlingGameEntity {

    private BowlingTurnEntity[] turns;
    private Integer id;
    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.turns=  turns;
    }
    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turns;
    }

    @Override
    public Integer getMaxTurn() {
        return BowlingRuleImpl.maxTurns;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }


    @Override
    public Integer getMaxPin() {
        return BowlingRuleImpl.maxPin;
    }
}
