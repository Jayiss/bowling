package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

public class GameEntityImpl implements GameEntity {
    private Integer gameId, maxTurn;
    private TurnEntity[] turns;

    public GameEntityImpl(Integer newGameId, TurnEntity[] turnEntities, Integer maxTurn) {
        this.gameId = newGameId;
        this.turns = turnEntities;
        this.maxTurn = maxTurn;
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
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId = id;
    }
}
