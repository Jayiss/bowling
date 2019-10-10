package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
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
        gameEntity.setScores(new ArrayList<Integer>());
        gameEntity.setTurns(new ArrayList<BowlingTurn>());
    }

    @Override
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> targetTurns = ((BowlingGameEntityImpl) getEntity()).getTurns();
        return targetTurns.toArray(new BowlingTurn[0]);
    }

    @Override
    public Integer[] getScores() {
        List<Integer> targetScores = ((BowlingGameEntityImpl) getEntity()).getScores();
        return targetScores.toArray(new Integer[0]);
    }

    @Override
    public Integer getTotalScore() {
        return ((BowlingGameEntityImpl) getEntity()).getTotalScore();
    }

    private Integer calcFinalScore(Integer[] scores) {
        Integer finalScore = 0;
        for (Integer score : scores) {
            finalScore += score;
        }
        return finalScore;
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
                // calcFinalScore() Integer -> setTotalScore() Integer
                gameEntity.setTotalScore(calcFinalScore(existingScores));
            }
        }
        return existingScores;
    }
}
