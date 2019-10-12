package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame {

    private BowlingGameEntity gameEntity = new BowlingGameEntityImpl();
    private BowlingRule bowlingRule;

    public BowlingGameImpl(BowlingRule bRule) {
        super(bRule);  // No default constructor available in parent class - 'Abstract Game'
        bowlingRule = bRule;

        BowlingGameEntityImpl gameEntity = (BowlingGameEntityImpl) getEntity();
        gameEntity.setTotalScore(0);
        gameEntity.setScores(new ArrayList<>());
        gameEntity.setTurns(new ArrayList<>());
    }

    @Override
    // Return a deep copy of target turns -> Defence mechanism
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> tempList = ((BowlingGameEntityImpl) getEntity()).getTurns();
        BowlingTurn[] targetTurns = tempList.toArray(new BowlingTurn[0]);

        BowlingTurn[] deepCopy = new BowlingTurnImpl[targetTurns.length];
        for (int i = 0; i < targetTurns.length; i++) {
            deepCopy[i] = new BowlingTurnImpl(null, null);  // Set 1st & 2nd Pin to null
            BowlingTurnEntity tempEntity = targetTurns[i].getEntity(), deepEntity = deepCopy[i].getEntity();

            deepEntity.setId(tempEntity.getId());  // Copy ID
            deepEntity.setFirstPin(tempEntity.getFirstPin());  // Copy first pin
            deepEntity.setSecondPin(tempEntity.getSecondPin());  // Copy second pin
        }
        return deepCopy;
    }

    @Override
    public Integer[] getScores() {
        List<Integer> targetScores = ((BowlingGameEntityImpl) getEntity()).getScores();
        return targetScores.toArray(new Integer[0]);
    }

    private Integer calcFinalScore(Integer[] scores) {
        Integer finalScore = 0;
        for (Integer score : scores) {
            finalScore += score;
        }
        return finalScore;
    }

    @Override
    // Create corresponding game entity
    public Integer getTotalScore() {
        BowlingGameEntityImpl gameEntity = (BowlingGameEntityImpl) getEntity();

        // Set the final score of current game
        Integer[] tempScores = bowlingRule.calcScores(gameEntity.getTurns().toArray(new BowlingTurn[0]));
        gameEntity.setScores(Arrays.asList(tempScores));
        gameEntity.setTotalScore(calcFinalScore(gameEntity.getScores().toArray(new Integer[0])));

        return ((BowlingGameEntityImpl) getEntity()).getTotalScore();
    }

    @Override
    public BowlingGameEntity getEntity() {
        return gameEntity;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        BowlingGameEntityImpl gameEntity = (BowlingGameEntityImpl) getEntity();
        BowlingTurn[] existingTurns = gameEntity.getTurns().toArray(new BowlingTurn[0]);
        Integer[] existingScores = gameEntity.getScores().toArray(new Integer[0]);

        // Check if current game is finished
        if (!bowlingRule.isGameFinished(existingTurns)) {
            // Check if the new pins are allowed
            if (bowlingRule.isNewPinsAllowed(existingTurns, pins)) {
                // addScores() BowlingTurn[] -> setTurns() List<BowlingTurn>
                gameEntity.setTurns(Arrays.asList(bowlingRule.addScores(existingTurns, pins)));
                existingTurns = gameEntity.getTurns().toArray(new BowlingTurn[0]);  // Reload

                // calcScores() Integer[] -> setScores() List<Integer>
                gameEntity.setScores(Arrays.asList(bowlingRule.calcScores(existingTurns)));
                existingScores = gameEntity.getScores().toArray(new Integer[0]);  // Reload

                gameEntity.setTotalScore(calcFinalScore(existingScores));
            }
        }
        return existingScores;
    }
}
