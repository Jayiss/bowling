package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

import java.io.Serializable;

public class GameEntityImpl implements GameEntity {

    private Integer MaxTurn = 10;
    private Integer Id;
    private TurnEntity[] TrunEntities;

    @Override
    public void setTurnEntities(TurnEntity[] turns) {
        this.TrunEntities = turns;
    }

    @Override
    public TurnEntity[] getTurnEntities() {
        return this.TrunEntities;
    }

    public void setMaxTurn(Integer maxTurn) {
        MaxTurn = maxTurn;
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
    public void setId(Serializable id) {

    }


    public void setId(Integer id) {
        this.Id = id;
    }

}