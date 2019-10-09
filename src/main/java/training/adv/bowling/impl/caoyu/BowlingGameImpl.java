package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<BowlingTurn> turns = new ArrayList<>();
    private BowlingRule rule = new BowlingRuleImpl();

    BowlingGameImpl(BowlingRule rule) {
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
        return scores.toArray(new Integer[0]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return turns.toArray(new BowlingTurn[0]);
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        if (rule.isNewPinsAllowed(turns.toArray(new BowlingTurn[0]), pins)) {

            //add and update turns
            BowlingTurn[] updatedTurns = rule.addScores(turns.toArray(new BowlingTurn[0]), pins);
            if (null != updatedTurns) {
                this.turns = new ArrayList<>();
                this.turns.addAll(Arrays.asList(updatedTurns));
            }

            //add and update scores
            Integer[] updatedScores = rule.calcScores(this.turns.toArray(new BowlingTurn[0]));
            if (null != updatedTurns) {
                this.scores = new ArrayList<>();
                this.scores.addAll(Arrays.asList(updatedScores));
            }
        }

        return scores.toArray(new Integer[0]);
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
