package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame{


    private int totalScore=0;
    private BowlingTurn[]bowlingTurnImpls;
    private BowlingTurnEntity[]bowlingTurnEntities=null;
    private List<Integer>scores=new ArrayList<>();
    private int gameId;
    static int turnId=0;
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public BowlingGameImpl(BowlingRule rule,int id) {
        super(rule);
        this.gameId=id;
    }

    public BowlingGameImpl(BowlingRuleImpl bowlingRule) {
        super(bowlingRule);
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
        return bowlingTurnImpls;
    }

    private List<Integer> integers=new ArrayList<>();
    @Override
    public Integer[] addScores(Integer... pins) {
        boolean allowed= rule.isNewPinsAllowed(bowlingTurnImpls,pins);
        if (allowed){
            bowlingTurnImpls=rule.addScores(bowlingTurnImpls,pins);
            scores.clear();
            totalScore=0;
            scores.addAll(Arrays.asList( rule.calcScores(bowlingTurnImpls)));
            for (int i = 0; i < scores.size(); i++) {
                totalScore+=scores.get(i);
            }
            setTurnForeignId();
        }
        return scores.toArray(new Integer[scores.size()]);

    }
    public void setTurnForeignId(){
        for (int i = 0; i < bowlingTurnImpls.length; i++) {
            ((BowlingTurnImpl)bowlingTurnImpls[i]).setForeign_id(gameId);
            ((BowlingTurnImpl)bowlingTurnImpls[i]).setTurn_Id(turnId++);
        }
    }
    public BowlingTurnEntity[] generateTurnKeys(BowlingTurn []bowlingTurns,int gameId){
        /*BowlingTurnEntity []bowlingTurnEntities=new BowlingTurnEntity[bowlingTurns.length];

        for (int i = 0; i < bowlingTurnEntities.length; i++) {
            bowlingTurnEntities[i].setFirstPin(bowlingTurns[i].getFirstPin());
            bowlingTurnEntities[i].setSecondPin(bowlingTurns[i].getSecondPin());
            bowlingTurnEntities[i].setId(new TurnKeyImpl(turnId++,gameId));
        }*/
        return bowlingTurnEntities;
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
        this.bowlingTurnEntities=turns;
        List <BowlingTurnImpl>bowlingTurnList=new ArrayList<>();
        for (int i = 0; i < turns.length; i++) {
            BowlingTurnImpl bowlingTurn =new BowlingTurnImpl(turns[i].getFirstPin(),turns[i].getSecondPin());
            bowlingTurnList.add(bowlingTurn);
//            bowlingTurns[i].setTurn_Id(turns[i].getId().getId());
//            bowlingTurns[i].setForeign_id(turns[i].getId().getForeignId());
//            bowlingTurns[i].setFirstPin(turns[i].getFirstPin());
//            bowlingTurns[i].setSecondPin(turns[i].getSecondPin());
        }
        this.bowlingTurnImpls=bowlingTurnList.toArray(new BowlingTurnImpl[0]);
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
        this.gameId=id;
    }
}
