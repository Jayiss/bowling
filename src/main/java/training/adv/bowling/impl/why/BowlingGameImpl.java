package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.Arrays;


public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {

    private Integer gameId;
    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }
    public BowlingGameImpl(BowlingRule rule,Integer id){
        this(rule);
        this.gameId=id;
    }

    private int [] score=new int[rule.getMaxTurn()];
    private BowlingTurn turn[]=new BowlingTurn[0];
    private int currentIndex=0;

    private TurnKey getCurrentKey(){
        return new TurnKeyImpl(currentIndex+1,gameId);
    }

    @Override
    public Integer getTotalScore() {
        Integer[] a=getScores();
        int sum=0;
        for (Integer i :
                a) {
            sum+=i;
        }
        return sum;
    }

    @Override
    public Integer[] getScores() {
        return rule.calcScores(turn);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return Arrays.copyOf(turn,turn.length);
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turn=rule.addScores(turn,pins);
        return getScores();
    }

    @Override
    public GameEntity getEntity() {
        return null;
    }
}
