package training.adv.bowling.impl.Fangchaoyi;

import org.h2.util.StringUtils;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.io.*;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame{
    private BowlingRule rule;
    private BowlingTurnImpl[] turns = new BowlingTurnImpl[0];
    private BowlingTurnEntity[] turnEntity;
    private int gameId;


    public BowlingGameImpl(BowlingRule rule, int gameId) {
        super(rule);
        this.rule = rule;
        this.gameId = gameId;
    }


    @Override
    public Integer getTotalScore() {
        if(turns[0].getFirstPin() == 0) return 0;
        Integer[] score = rule.calcScores(turns);
        Integer totalScore = 0;
        for (Integer integer : score) totalScore += integer;
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        return new Integer[0];
    }

    @Override
    public BowlingTurn[] getTurns() {
        BowlingTurn[] turns1 = turns.clone();
        return turns1;
    }

    @Override
    public int[] addScores(Integer... pins) {
        if(pins.length == 0){
            turns = new BowlingTurnImpl[]{new BowlingTurnImpl(new int[]{0,0})};
            return new int[0];
        }
        for (Integer pin : pins) if (pin > rule.getMaxPin()) return new int[0];
        if(pins.length > 1 && pins[0] > 0 && pins[0] + pins[1] > rule.getMaxPin() && pins[0] != 10) return new int[0];
        if(turns.length + pins.length > rule.getMaxTurn() + 2&&turns.length!=1) return new int[0];
        if(turns.length == 0){
            turns = new BowlingTurnImpl[1];
            turns[0] = new BowlingTurnImpl(new int[]{0,0});
        }
        turns = (BowlingTurnImpl[]) rule.addScores(turns, pins);
        for(int i = 0;i < turns.length;i++){
            TurnKey key = new TurnKeyImpl(i+1,gameId);
            turns[i].getEntity().setId(key);
        }
        return new int[0];
    }

    @Override
    public BowlingGameEntity getEntity() {
        int i = 0;
        TurnKey key;
        BowlingTurnEntity[] turnEntities = new BowlingTurnEntityImpl[this.turns.length];
        if(turns.length == 0 && getTurnEntities() != null){
            turns = new BowlingTurnImpl[getTurnEntities().length];
            for(BowlingTurnEntity turn : this.getTurnEntities()){
                turns[i] = new BowlingTurnImpl(new int[]{turn.getFirstPin(),turn.getSecondPin()}, turn.getId());
                i++;
            }
        }
        else{
            for(BowlingTurn turn : this.getTurns()){
                key = new TurnKeyImpl(i+1, this.gameId);
                turnEntities[i] = new BowlingTurnEntityImpl(turn);
                turnEntities[i].setId(key);
                i++;
            }
        }
        this.setTurnEntities(turnEntities);
        return this;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.turnEntity = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turnEntity;
    }

    @Override
    public Integer getMaxTurn() {
        return rule.getMaxTurn();
    }

    @Override
    public Integer getId() {
        return gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId = id;
    }

    @Override
    public Integer getMaxPin() {
        return null;
    }
}
