package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {

    private BowlingTurn []turns=new BowlingTurn[0];
    @Override
    public GameEntity getEntity() {
        return null;
    }
    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }
    @Override
    public Integer getTotalScore() {
        Integer [] scores=getScores();
        Integer total_scores=0;
        for (Integer score:scores
        ) {
            total_scores+=score;
        }
        return total_scores;
    }

    @Override
    public Integer[] getScores() {
        return  super.rule.calcScores(turns);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns= super.rule.addScores(turns,pins);
        return null;
    }
}
