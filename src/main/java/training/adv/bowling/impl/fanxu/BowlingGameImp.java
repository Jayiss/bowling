package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImp extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
    private  BowlingTurn[] bowlingTurns;

    public BowlingGameImp(BowlingRule rule) {
        super(rule);
        bowlingTurns = new BowlingTurnImpl[rule.getMaxTurn()+2];
        for(int i=0;i<bowlingTurns.length;i++){
            bowlingTurns[i] = new BowlingTurnImpl();
        }
    }

    //get the sum of all score
    @Override
    public Integer getTotalScore() {
        int sum = 0;
        Integer[] scores = rule.calcScores(bowlingTurns);
        for (Integer score:scores){
            sum+=score;
        }
        return sum;
    }

    @Override
    public Integer[] getScores() {
        return rule.calcScores(bowlingTurns);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return bowlingTurns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        bowlingTurns = rule.addScores(bowlingTurns,pins);
        return getScores();
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
