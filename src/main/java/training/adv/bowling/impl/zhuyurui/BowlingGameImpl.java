package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.*;

public class BowlingGameImpl  implements BowlingGame {

    private Integer[] scores;
    private BowlingTurn initTurn=new BowlingTurnImpl(null);
    private BowlingTurn[] bowlingTurns={initTurn};
    private BowlingRule bowlingRule=new BowlingRuleImpl();


    @Override
    public Integer getTotalScore() {
        int total=0;
        for (Integer score : scores) {
            total += score;
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

        bowlingTurns= bowlingRule.addScores(bowlingTurns,pins);
        scores=bowlingRule.calcScores(bowlingTurns);
        return scores;


    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
