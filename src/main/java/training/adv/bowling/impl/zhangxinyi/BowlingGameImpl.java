package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {

    private Integer totalScore;
    private List<Integer> scores;
    private List<BowlingTurn> turns;
    private BowlingRule rule;

    BowlingGameImpl(BowlingRule rule) {
        super(rule);
        totalScore = 0;
        scores = new ArrayList<Integer>();
        turns = new ArrayList<BowlingTurn>();
        this.rule = rule;
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
    public Integer[] addScores(Integer... pins) {
        BowlingTurn[] turnsArray = turns.toArray(new BowlingTurn[0]);
        if (!rule.isGameFinished(turnsArray)) {
            if (rule.isNewPinsAllowed(turnsArray, pins)) {
                turns = Arrays.asList(rule.addScores(turnsArray, pins));
                turnsArray = turns.toArray(new BowlingTurn[0]);
                scores = Arrays.asList(rule.calcScores(turnsArray));
            }
        }
        return getScores();
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
