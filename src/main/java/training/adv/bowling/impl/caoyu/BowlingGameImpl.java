package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import java.util.ArrayList;
import java.util.Arrays;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<BowlingTurn> turns = new ArrayList<>();
    private BowlingRule rule = new BowlingRuleImpl();

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    @Override
    public Integer getTotalScore() {
        Integer totalScore = 0;
        for (int i = 0; i < rule.getMaxTurn() && i < scores.size(); i++) {
            totalScore += scores.get(i);
        }
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        return scores.toArray(new Integer[scores.size()]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return turns.toArray(new BowlingTurn[turns.size()]);
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        if (rule.isNewPinsAllowed(turns.toArray(new BowlingTurn[turns.size()]), pins)) {

            //update turns
            BowlingTurn[] updatedTurns = rule.addScores(turns.toArray(new BowlingTurnImpl[turns.size()]), pins);
            this.turns = new ArrayList<>();
            this.turns.addAll(Arrays.asList(updatedTurns));
            //update scores
            Integer[] updatedScores = rule.calcScores(this.turns.toArray(new BowlingTurnImpl[turns.size()]));
            this.scores = new ArrayList<>();
            this.scores.addAll(Arrays.asList(updatedScores));
        }

        return scores.toArray(new Integer[scores.size()]);
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
