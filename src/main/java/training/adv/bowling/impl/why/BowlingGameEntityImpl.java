package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;


public class BowlingGameEntityImpl implements GameEntity {

    private BowlingTurnEntity[] turns;
    private Integer id;
    @Override
    public void setTurnEntities(TurnEntity[] turns) {
        this.turns= (BowlingTurnEntity[]) turns;
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

    BowlingTurn[] getBowlingTurn(){
        BowlingTurn[] temp=new BowlingTurn[turns.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i]=new BowlingTurnImpl(turns[i].getFirstPin(),turns[i].getSecondPin());
        }
        return temp;
    }
}
