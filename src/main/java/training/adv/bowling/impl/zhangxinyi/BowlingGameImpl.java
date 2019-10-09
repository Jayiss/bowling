package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.*;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
    private GameEntity game = new BowlingGameEntityImpl();

    BowlingGameImpl(BowlingRule rule) {
        super(rule);
        BowlingGameEntityImpl entity = (BowlingGameEntityImpl)getEntity();
        entity.setTotalScore(0);
        entity.setScores(new ArrayList<Integer>());
        entity.setTurns(new ArrayList<BowlingTurn>());
    }

    @Override
    public Integer getTotalScore() {
        return ((BowlingGameEntityImpl)getEntity()).getTotalScore();
    }

    @Override
    public Integer[] getScores() {
        return ((BowlingGameEntityImpl)getEntity()).getScores().toArray(new Integer[0]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return ((BowlingGameEntityImpl)getEntity()).getTurns().toArray(new BowlingTurn[0]);
    }

    @Override
    // Add a list of pins, first check if legal, then calculate.
    // Return the score array(also update the total score).
    public Integer[] addScores(Integer... pins) {
        BowlingGameEntityImpl entity = (BowlingGameEntityImpl)getEntity();
        if (!rule.isGameFinished(entity.getTurns().toArray(new BowlingTurn[0]))) {
            if (rule.isNewPinsAllowed(entity.getTurns().toArray(new BowlingTurn[0]), pins)) {
                BowlingTurn[] turnsArray = rule.addScores(entity.getTurns().toArray(new BowlingTurn[0]), pins);
                entity.setTurns(Arrays.asList(turnsArray));
                Integer[] scoresArray = rule.calcScores(entity.getTurns().toArray(new BowlingTurn[0]));
                entity.setScores(Arrays.asList(scoresArray));
                entity.setTotalScore(updateTotalScore(entity.getScores().toArray(new Integer[0])));
            }
        }
        return entity.getScores().toArray(new Integer[0]);
    }

    private Integer updateTotalScore(Integer[] scores) {
        int sum = 0;
        for (Integer score : scores) {
            sum += score;
        }
        return sum;
    }

    @Override
    public GameEntity getEntity() {
        return game;
    }
}
