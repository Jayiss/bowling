package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame,
        BowlingGameEntity {
    private int gameId;
    private Integer maxTurn, maxPin;
    private ArrayList<BowlingTurnImpl> turns = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();

    BowlingGameImpl(BowlingRule rule) {
        super(rule);
        this.gameId = UidUtil.getNewGameId();
        this.maxTurn = rule.getMaxTurn();
        this.maxPin = rule.getMaxPin();
    }

    public BowlingGameImpl(BowlingRule rule, int gameId) {
        super(rule);
        this.maxTurn = rule.getMaxTurn();
        this.maxPin = rule.getMaxPin();
        this.gameId = gameId;
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
        BowlingTurnImpl[] result = new BowlingTurnImpl[this.turns.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = new BowlingTurnImpl(this.turns.get(i).getFirstPin(), this.turns.get(i).getSecondPin());
            result[i].setId(this.turns.get(i).getId());
        }
        return result;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        if (rule.isNewPinsAllowed(turns.toArray(new BowlingTurn[0]), pins)) {

            //add and update turns
            BowlingTurn[] updatedTurns = rule.addScores(turns.toArray(new BowlingTurn[0]), pins);
            if (null != updatedTurns) {
                this.turns.clear();
                for (BowlingTurn updatedTurn :
                        updatedTurns) {
                    BowlingTurnImpl turn = new BowlingTurnImpl(updatedTurn.getFirstPin(), updatedTurn.getSecondPin());
                    turn.setId(new BowlingTurnKeyImpl(this.gameId));
                    this.turns.add(turn);
                }
            }

            //add and update scores
            Integer[] updatedScores = rule.calcScores(this.turns.toArray(new BowlingTurn[0]));
            if (null != updatedTurns) {
                this.scores = new ArrayList<>();
                this.scores.addAll(Arrays.asList(updatedScores));
            }
        }
        return scores.toArray(new Integer[0]);
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
        //empty turnEntities
        this.turns.clear();

        for (BowlingTurnEntity bowlingTurnEntity : bowlingTurnEntities) {

            //construct new turn entity
            BowlingTurnImpl currentTurn = new BowlingTurnImpl();
            TurnKey currentTurnKey = new BowlingTurnKeyImpl(this.gameId);
            currentTurn.setId(currentTurnKey);
            currentTurn.setFirstPin(bowlingTurnEntity.getFirstPin());
            currentTurn.setSecondPin(bowlingTurnEntity.getSecondPin());

            //add new
            this.turns.add(currentTurn);
        }


        //add and update scores
        Integer[] updatedScores = rule.calcScores(this.turns.toArray(new BowlingTurn[0]));
        if (null != this.turns) {
            this.scores = new ArrayList<>();
            this.scores.addAll(Arrays.asList(updatedScores));
        }
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turns.toArray(BowlingTurnEntity[]::new);
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
