package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private int gameId;
    private int maxPin;
    private int maxTurn;
    private int totalScore;

    @Override
    public Integer getMaxPin() {
        return this.maxPin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {

    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return new BowlingTurnEntity[0];
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return this.gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId = id;
    }

    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public void setGameId(int gameId) { this.gameId = gameId; }

    public void setMaxPin(int maxPin) { this.maxPin = maxPin; }

    public void setMaxTurn(int maxTurn) { this.maxTurn = maxTurn; }
}
