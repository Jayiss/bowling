package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.Arrays;


public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule>
        implements BowlingGame {


    BowlingGameEntityImpl entity=new BowlingGameEntityImpl();
    public BowlingGameImpl(BowlingRule rule,Integer id){
        super(rule);
        entity.setId(id);
        entity.setTurnEntities(new BowlingTurnEntityImpl[0]);
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
        return rule.calcScores(entity.getBowlingTurn());
    }

    @Override
    public BowlingTurn[] getTurns() {
        return entity.getBowlingTurn();
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        BowlingTurn[] turn=rule.addScores(entity.getBowlingTurn(),pins);
        BowlingTurnEntity[] temp=new BowlingTurnEntity[turn.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i]=turn[i].getEntity();
            temp[i].setId(new TurnKeyImpl(i,entity.getId()));
        }
        entity.setTurnEntities(temp);
        return getScores();
    }

    @Override
    public GameEntity getEntity() {
//        GameEntity gameEntity=new BowlingGameEntityImpl();
//        TurnEntity[] entities=new TurnEntity[turn.length];
//        for (int i=0;i<entities.length;i++) {
//            BowlingTurn singleTurn= turn[i];
//            BowlingTurnEntityImpl entity=new BowlingTurnEntityImpl();
//            entity.setFirstPin(singleTurn.getFirstPin());
//            entity.setSecondPin(singleTurn.getSecondPin());
//            entity.setId(new TurnKeyImpl(i+1,gameId));
//        }
//        gameEntity.setTurnEntities(entities);
//        gameEntity.setId(gameId);
        return entity;
    }
}
