package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private int id;
    private BowlingTurnEntity[] turns;

    BowlingGameEntityImpl(){id=new ID().getId();}
    BowlingGameEntityImpl(int id){this.id=id;}

    @Override
    public Integer getMaxPin() {
        return new BowlingRuleImpl().getMaxPin();
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.turns=turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
         return turns;
    }

    @Override
    public Integer getMaxTurn() {
        return new BowlingRuleImpl().getMaxTurn();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
