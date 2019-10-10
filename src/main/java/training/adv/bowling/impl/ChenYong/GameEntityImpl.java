package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

public class GameEntityImpl implements GameEntity {
    @Override
    public void setTurnEntities(TurnEntity[] turns) {

    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public TurnEntity[] getTurnEntities() {
        return new TurnEntity[0];
    }

    @Override
    public Integer getMaxTurn() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
