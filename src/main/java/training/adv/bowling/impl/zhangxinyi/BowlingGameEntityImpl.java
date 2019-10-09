package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

import java.util.List;

public class BowlingGameEntityImpl implements GameEntity {
    private Integer totalScore;
    private List<Integer> scores;
    private List<BowlingTurn> turns;


    @Override
    public void setTurnEntities(TurnEntity[] turns) {

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

    @Override
    public void setId(Integer id) {

    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public List<BowlingTurn> getTurns() {
        return turns;
    }

    public void setTurns(List<BowlingTurn> turns) {
        this.turns = turns;
    }
}
