package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.*;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {

    private Integer totalScore;
    private List<Integer> scores;
    private List<BowlingTurn> turns;
    private BowlingRule rule;

    BowlingGameImpl(BowlingRule rule) {
        super(rule);
        this.rule = rule;
        totalScore = 0;
        scores = new ArrayList<Integer>();
        turns = new ArrayList<BowlingTurn>();
    }

    @Override
    public Integer getTotalScore() {
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        return scores.toArray(new Integer[0]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return turns.toArray(new BowlingTurn[0]);
    }

    @Override
    // Add a list of pins, first check if legal, then calculate.
    // Return the score array(also update the total score).
    public Integer[] addScores(Integer... pins) {
        if (!rule.isGameFinished(turns.toArray(new BowlingTurn[0]))) {
            if (rule.isNewPinsAllowed(turns.toArray(new BowlingTurn[0]), pins)) {
                BowlingTurn[] turnsArray = rule.addScores(turns.toArray(new BowlingTurn[0]), pins);
                turns = Arrays.asList(turnsArray);
                Integer[] scoresArray = rule.calcScores(turns.toArray(new BowlingTurn[0]));
                scores = Arrays.asList(scoresArray);
                totalScore = updateTotalScore(scores.toArray(new Integer[0]));
            }
        }
        return scores.toArray(new Integer[0]);
    }

    @Override
    // Need to be implemented.
    public GameEntity getEntity() {
        return null;
    }

    private Integer updateTotalScore(Integer[] scores) {
        int sum = 0;
        for (Integer score : scores) {
            sum += score;
        }
        return sum;
    }
}
