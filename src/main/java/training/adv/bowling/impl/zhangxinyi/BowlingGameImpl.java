package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.*;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity game = (BowlingGameEntity) new BowlingGameEntityImpl();

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
        BowlingGameEntityImpl entity = (BowlingGameEntityImpl) getEntity();
        entity.setTotalScore(0);
        entity.setScores(new ArrayList<Integer>());
        entity.setTurns(new ArrayList<BowlingTurn>());
    }

    @Override
    // Ugly code, just copy part of addscores.
    public Integer getTotalScore() {
//        BowlingGameEntityImpl entity = (BowlingGameEntityImpl) getEntity();
//        BowlingTurn[] turnsArray = rule.addScores(entity.getTurns().toArray(new BowlingTurn[0]));
//        entity.setTurns(Arrays.asList(turnsArray));
//        Integer[] scoresArray = rule.calcScores(entity.getTurns().toArray(new BowlingTurn[0]));
//        entity.setScores(Arrays.asList(scoresArray));
//        entity.setTotalScore(updateTotalScore(entity.getScores().toArray(new Integer[0])));
        return ((BowlingGameEntityImpl) getEntity()).getTotalScore();
    }

    @Override
    public Integer[] getScores() {
        return ((BowlingGameEntityImpl) getEntity()).getScores().toArray(new Integer[0]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return ((BowlingGameEntityImpl) getEntity()).getTurns().toArray(new BowlingTurn[0]);
    }

    @Override
    // Add a list of pins, first check if legal, then calculate.
    // Return the score array(also update the total score).
    public Integer[] addScores(Integer... pins) {
        BowlingGameEntityImpl entity = (BowlingGameEntityImpl) getEntity();
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

    public Integer updateTotalScore(Integer[] scores) {
        int sum = 0;
        for (Integer score : scores) {
            sum += score;
        }
        return sum;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return game;
    }
}
