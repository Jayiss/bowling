package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameInfo implements BowlingGameEntity {
    private BowlingTurnEntity[] turns;
    private Integer id;
    private Integer MAX_TURN;
    private Integer MAX_PIN;
    BowlingGameInfo(){

    }
    public BowlingGameInfo(int id,int maxTurn,int maxPin){
        this.id = id;
        this.MAX_TURN = maxTurn;
        this.MAX_PIN = maxPin;
    }


    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.turns = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turns;
    }

    @Override
    public Integer getMaxTurn() {
        return  MAX_TURN;
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
