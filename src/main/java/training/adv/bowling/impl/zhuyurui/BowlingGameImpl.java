package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl  extends AbstractGame<BowlingTurn,BowlingRule,BowlingGameEntity> implements BowlingGame {


    private BowlingTurn initTurn=new BowlingTurnImpl(null,null);

    //private BowlingTurnEntity[] bowlingTurnEntities={initTurn.getEntity()};
    private BowlingGameEntity bowlingGameEntity=new BowlingGameEntityImpl();

    private Integer[] scores;
    //private BowlingTurn[] bowlingTurns={initTurn};



    public BowlingGameImpl(BowlingRule rule) {
        super(rule);
        BowlingTurnEntity[] turnEntities={initTurn.getEntity()};
        bowlingGameEntity.setTurnEntities(turnEntities);
        //bowlingGameEntity.setId(IDUtils.createID());

    }

    public BowlingGameImpl(BowlingRule rule,BowlingGameEntity bowlingGameEntity) {
        this(rule);
        this.bowlingGameEntity=bowlingGameEntity;

    }


    @Override
    public Integer getTotalScore() {
        scores=BowlingRuleImpl.getInstance().calcScores(getTurns(bowlingGameEntity.getTurnEntities()));
        int total=0;
        for (Integer score : scores) {
            total += score;
        }
        return total;
    }

    @Override
    public Integer[] getScores() {
        return scores;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return getTurns(bowlingGameEntity.getTurnEntities());
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        BowlingTurnEntity[] entities=bowlingGameEntity.getTurnEntities();
        BowlingTurn[] bowlingTurns= BowlingRuleImpl.getInstance().addScores(getTurns(entities),pins);
        entities=new BowlingTurnEntity[bowlingTurns.length];
        for(int i=0;i<bowlingTurns.length;i++){
            int j=i+1;
            TurnKey turnKey=new TurnKeyImpl(j,bowlingGameEntity.getId());
            entities[i]=new BowlingTurnEntityImpl(bowlingTurns[i].getFirstPin(),bowlingTurns[i].getSecondPin(),turnKey);
        }

        bowlingGameEntity.setTurnEntities(entities);
        scores=BowlingRuleImpl.getInstance().calcScores(bowlingTurns);
        return scores;
    }

    @Override
    public BowlingGameEntity getEntity() {

        return bowlingGameEntity;
    }

    private BowlingTurn[] getTurns(BowlingTurnEntity[] bowlingTurnEntities){
        BowlingTurn[] bowlingTurns=new BowlingTurn[bowlingTurnEntities.length];
        for(int i=0;i<bowlingTurnEntities.length;i++){
            bowlingTurns[i]=new BowlingTurnImpl(bowlingTurnEntities[i]);
        }
        return bowlingTurns;
    }
}
