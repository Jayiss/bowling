package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame{

    private BowlingRuleImpl rule = new BowlingRuleImpl();
    private BowlingTurn[] turns;
    // private BowlingTurnEntity[] turnEntities;
    private int gameId;
    private int totalScore;
    private int maxTurn;
    private int maxPin;
    static int turnKey = 0;


    public BowlingGameImpl(BowlingRule rule, int gameId) {
        super(rule);
        this.gameId = gameId;
    }

    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
    }

    @Override
    public Integer getTotalScore() {
        Integer[] scores = getScores();
        Integer totalScore = 0;
        for(int i = 0; i < scores.length; i++){
            totalScore += scores[i];
        }
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        Integer[] scores = rule.calcScores(turns);
        return scores;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return turns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        turns = rule.addScores(turns, pins);
        setForeignKey(turns);
        // turnEntities = turn2entity();
        return pins;
    }

    public void setForeignKey(BowlingTurn[] turns){
        for(int i = 0; i < turns.length; i++){
            ((BowlingTurnImpl)turns[i]).setGameId(gameId);
            ((BowlingTurnImpl)turns[i]).setTurnId(turnKey++);
        }
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }

    @Override
    public Integer getMaxPin() {
        return rule.getMaxPin();
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        List<BowlingTurnImpl> bowlingTurnList = new ArrayList<>();
        for(int i = 0; i < turns.length; i++){
            BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
            bowlingTurn.setFirstPin(turns[i].getFirstPin());
            bowlingTurn.setSecondPin(turns[i].getSecondPin());
            bowlingTurnList.add(bowlingTurn);
        }
        this.turns = bowlingTurnList.toArray(new BowlingTurnImpl[0]);
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return new BowlingTurnEntity[0];
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

    }

    public void setGameId(int gameId) { this.gameId = gameId; }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public void setMaxPin(int maxPin) {
        this.maxPin = maxPin;
    }

    public int getGameId() { return this.gameId; }

    /*public BowlingTurnEntity[] turn2entity (){
        BowlingTurnEntity[] turnEntities = new BowlingTurnEntity[turns.length];
        for(int i = 0; i < turns.length; i++){
            turnEntities[i].setFirstPin(turns[i].getFirstPin());
            turnEntities[i].setSecondPin(turns[i].getSecondPin());
            turnEntities[i].setId(new TurnKeyImpl(turnKey++, gameId)) ;
        }
        return turnEntities;
    }*/
}
