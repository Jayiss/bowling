package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
    private int totalScore=0;
    private BowlingTurn[]bowlingTurnImpls;
    private List<Integer>scores=new ArrayList<>() ;
    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    @Override
    public Integer getTotalScore() {
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        return (Integer[]) scores.toArray();
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }

    private List<Integer> integers=new ArrayList<>();
    @Override
    public Integer[] addScores(Integer... pins) {
        boolean allowed= ((BowlingRule)rule).isNewPinsAllowed(bowlingTurnImpls,pins);
        if (allowed){
            bowlingTurnImpls=((BowlingRule)rule).addScores(bowlingTurnImpls,pins);
            scores.clear();
            totalScore=0;
            scores.addAll(Arrays.asList( rule.calcScores(bowlingTurnImpls)));
            for (int i = 0; i < scores.size(); i++) {
                totalScore+=scores.get(i);
            }
        }
        return scores.toArray(new Integer[scores.size()]);

    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
