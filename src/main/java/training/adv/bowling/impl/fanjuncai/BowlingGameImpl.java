package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule,BowlingGameEntity> implements BowlingGame {

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
        Id = Sequence.ID+1;
        Sequence.ID++;
    }

    private Integer Id;
    private  Integer[] Scores;
    private BowlingTurn[] ExistingTurns;


    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public void setScores(Integer[] scores) {
        Scores = scores;
    }

    public BowlingTurn[] getExistingTurns() {
        return ExistingTurns;
    }

    public void setExistingTurns(BowlingTurn[] existingTurns) {
        ExistingTurns = existingTurns;
    }

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
        return ExistingTurns;
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
    public BowlingGameEntity getEntity() {
        BowlingGameEntityImpl bowlingGameEntity = new BowlingGameEntityImpl();
        if(bowlingGameEntity.getId()==null)
            bowlingGameEntity.setId(this.Id);
        return bowlingGameEntity;
    }
}
