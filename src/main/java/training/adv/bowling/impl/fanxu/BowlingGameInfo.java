package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

public class BowlingGameInfo implements GameEntity {
    private TurnEntity[] turns;
    private Integer id;
    private Integer MAX_TURN;
    BowlingGameInfo(){

    }
    BowlingGameInfo(int id,int maxTurn){
        this.id = id;
        this.MAX_TURN = maxTurn;
    }
    @Override
    public void setTurnEntities(TurnEntity[] turns) {
        this.turns = turns;
    }

    @Override
    public TurnEntity[] getTurnEntities() {
        return turns;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
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
