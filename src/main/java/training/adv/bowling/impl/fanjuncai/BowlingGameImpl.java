package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    private  Integer[] Scores;
    private BowlingTurn[] ExistingTurns;
    @Override
    public Integer getTotalScore() {
        Integer TotalScore = 0;
        if(Scores==null||Scores.length==0)
            return 0;
        for(Integer socre:Scores)
            TotalScore+=socre;

        return TotalScore;
    }

    @Override
    public Integer[] getScores() {
        return Scores;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }

    @Override
    public Integer[] addScores(Integer... pins) {

        BowlingTurn[] Temp = this.ExistingTurns;
        BowlingRuleImpl bowlingRule = new BowlingRuleImpl();
        if(bowlingRule.isNewPinsAllowed(Temp,pins)){
            BowlingTurn[] bowlingTurn = bowlingRule.addScores(Temp,pins);
            this.ExistingTurns = bowlingTurn;
            this.Scores = bowlingRule.calcScores(bowlingTurn);
            return  this.Scores;
        }

        return this.Scores;

    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
