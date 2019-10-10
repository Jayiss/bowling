package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameEntityImpl implements GameEntity {
    public static Integer uniqueId = 0;

    private Integer id;
    private Integer totalScore;
    private List<Integer> scores;
    private List<BowlingTurn> turns;

    @Override
    public void setTurnEntities(TurnEntity[] turns) {
        BowlingTurnEntity[] bTurns = (BowlingTurnEntity[])turns;
        int len = bTurns.length;
        this.turns = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            this.turns.add(new BowlingTurnImpl(bTurns[i].getFirstPin(), bTurns[i].getSecondPin()));
        }
    }

    @Override
    public TurnEntity[] getTurnEntities() {
        TurnEntity[] turnEntities = new TurnEntity[turns.size()];
        for (int i = 0; i < turns.size(); i++) {
            turnEntities[i] = turns.get(i).getEntity();
        }
        return turnEntities;
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
        this.id = id;
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
