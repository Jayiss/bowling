package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl implements BowlingGame {

    // private List<BowlingTurn> listBowlingTurn = new ArrayList<>();
    private BowlingRuleImpl rule = new BowlingRuleImpl();
    private BowlingTurn[] turns;
    // private List<Integer> scores = new ArrayList<>();

    @Override
    public Integer getTotalScore() {
        Integer[] scores = getScores();
        Integer totalScore = 0;
        for(int i = 0; i < scores.length; i++){
            totalScore += scores[i];
        }
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        Integer[] scores = rule.calcScores(turns);
        return scores;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns = rule.addScores(turns, pins);
        return pins;
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
