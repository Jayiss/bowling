package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame,
        BowlingGameEntity {
    int gameId;
    private Integer maxTurn, maxPin;
    private BowlingTurnEntity[] bowlingTurnEntities;

    //    private BowlingGameEntity gameEntity;
    private ArrayList<BowlingTurn> turns = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();

    BowlingGameImpl(BowlingRule rule) {
        super(rule);
        this.gameId = UidUtil.getNewGameId();
        this.bowlingTurnEntities = new BowlingTurnEntity[0];
        this.maxTurn = rule.getMaxTurn();
        this.maxPin = rule.getMaxPin();
    }

    //TODO
    BowlingGameImpl(BowlingRule rule, GameEntity gameEntity) {
        super(rule);
    }

    @Override
    public Integer getTotalScore() {
        Integer totalScore = 0;
        for (int i = 0; i < rule.getMaxTurn() && i < scores.size(); i++) {
            totalScore += scores.get(i);
        }
        return totalScore;
    }

    @Override
    public Integer[] getScores() {
        return scores.toArray(new Integer[0]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return turns.toArray(new BowlingTurn[0]);
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        if (rule.isNewPinsAllowed(turns.toArray(new BowlingTurn[0]), pins)) {

            //add and update turns
            BowlingTurn[] updatedTurns = rule.addScores(turns.toArray(new BowlingTurn[0]), pins);
            if (null != updatedTurns) {
                this.turns = new ArrayList<>();
                this.turns.addAll(Arrays.asList(updatedTurns));
            }

            //add and update scores
            Integer[] updatedScores = rule.calcScores(this.turns.toArray(new BowlingTurn[0]));
            if (null != updatedTurns) {
                this.scores = new ArrayList<>();
                this.scores.addAll(Arrays.asList(updatedScores));
            }

            //update gameEntity
            updateGameEntity();
        }
        return scores.toArray(new Integer[0]);
    }

    //update gameEntity
    private void updateGameEntity() {
        //empty turnEntities
        BowlingTurnEntity[] turnEntities = new BowlingTurnImpl[turns.size()];

        for (int i = 0; i < turns.size(); i++) {

            //construct new turn entity
            BowlingTurn currentTurn = turns.get(i);
            BowlingTurnEntity currentTurnEntity = new BowlingTurnImpl();
            TurnKey currentTurnKey = new BowlingTurnKeyImpl(this.gameId);
            currentTurnEntity.setId(currentTurnKey);
            currentTurnEntity.setFirstPin(currentTurn.getFirstPin());
            currentTurnEntity.setSecondPin(currentTurn.getSecondPin());

            //add new
            turnEntities[i] = currentTurnEntity;
        }

        this.setTurnEntities(turnEntities);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }


    @Override
    public Integer getMaxPin() {
        return maxPin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] bowlingTurnEntities) {
        this.bowlingTurnEntities = bowlingTurnEntities;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId = id;
    }
}
