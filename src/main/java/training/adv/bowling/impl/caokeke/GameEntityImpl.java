package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

public class GameEntityImpl implements GameEntity{

    private int id;
    private TurnEntity [] turns;

    GameEntityImpl(){
        //id=new ID().getId();
        id=(int) System.currentTimeMillis();
    }
    GameEntityImpl(int id){this.id=id;}

    @Override
    public void setTurnEntities(TurnEntity[] turns) {
        this.turns=turns;
    }

    @Override
    public TurnEntity[] getTurnEntities() {
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
