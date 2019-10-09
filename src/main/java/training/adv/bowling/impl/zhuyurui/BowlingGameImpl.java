package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl  implements BowlingGame {

    private Integer[] scores;
    private BowlingTurn initTurn=new BowlingTurnImpl(null);
    private BowlingTurn[] bowlingTurns={initTurn};
    private BowlingRule bowlingRule=new BowlingRuleImpl();


    @Override
    public Integer getTotalScore() {
        int total=0;
        for(int i=0;i<scores.length;i++){
            total+=scores[i];
        }
        return total;
    }

    @Override
    public Integer[] getScores() {
        return scores;
    }

    @Override
    public BowlingTurn[] getTurns() {

        return bowlingTurns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {

        BowlingTurn[] latest=bowlingRule.addScores(bowlingTurns,pins);
        bowlingTurns=latest;
        scores=bowlingRule.calcScores(bowlingTurns);
        return scores;


    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
