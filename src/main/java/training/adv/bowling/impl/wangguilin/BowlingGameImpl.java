package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.*;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame {

    private Integer totalScore;
    private List<Integer> scores;
    //private List<BowlingTurn> turns;

    private Integer maxTurns;
    private Integer maxPin;

    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurnEntity[] bowlingTurnEntities;

    BowlingGameImpl(Integer id, BowlingRule rule) {
        super(rule);
        totalScore = 0;
        scores = new ArrayList<Integer>();
        bowlingGameEntity = new BowlingGameEntityImpl(id, rule.getMaxTurn(), rule.getMaxPin());
        bowlingTurnEntities = bowlingGameEntity.getTurnEntities();
        maxTurns = 10;
        maxPin = 10;

    }

    @Override
    public Integer getTotalScore() {
        int sum=0;
        for (Integer score : rule.calcScores(getTurns())) {
            sum += score;
        }
        return sum;
    }

    @Override
    public Integer[] getScores() {
        return scores.toArray(new Integer[scores.size()]);
    }

    @Override
    public BowlingTurn[] getTurns() {
        BowlingTurnEntity[] turnEntities = bowlingGameEntity.getTurnEntities();
        BowlingTurn[] turns = new BowlingTurn[turnEntities.length];
        for (int i = 0; i < turnEntities.length; i++) {
            if (turnEntities[i].getFirstPin() != null) {
                if (turnEntities[i].getSecondPin() != null) {
                    turns[i] = new BowlingTurnImpl(turnEntities[i].getFirstPin(), turnEntities[i].getSecondPin());
                } else {
                    turns[i] = new BowlingTurnImpl(turnEntities[i].getFirstPin());
                }
            }
            turns[i].getEntity().setId(new TurnKeyImpl(i, bowlingGameEntity.getId()));
        }
        return turns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        BowlingTurn[] turns = getTurns();
        if (!rule.isGameFinished(turns)) {
            if (rule.isNewPinsAllowed(turns, pins)) {
                BowlingTurn[] turnsArray = rule.addScores(turns, pins);
                BowlingTurnEntity[] turnEntities = new BowlingTurnEntityImpl[turnsArray.length];
                for (int i = 0; i < turnsArray.length; i++) {
                    turnEntities[i] = turnsArray[i].getEntity();
                    turnEntities[i].setId(new TurnKeyImpl(i, bowlingGameEntity.getId()));
                }
                bowlingGameEntity.setTurnEntities(turnEntities);
            }
        }
        return scores.toArray(new Integer[scores.size()]);
    }

    @Override
    public BowlingGameEntity getEntity() {

        return bowlingGameEntity;
    }

}