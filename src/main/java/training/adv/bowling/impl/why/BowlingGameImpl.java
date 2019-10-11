package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.Arrays;


public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule,BowlingGameEntity>
        implements BowlingGame {


    BowlingGameEntity entity;

    public BowlingGameImpl(BowlingRule rule,Integer id){
        super(rule);
        entity=new BowlingGameEntityImpl();
        entity.setId(id);
        entity.setTurnEntities(new BowlingTurnEntityImpl[0]);
    }

    BowlingGameImpl(BowlingRule rule,BowlingGameEntity gameEntity){
        super(rule);
        this.entity=gameEntity;
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
        return rule.calcScores(getBowlingTurn(entity.getTurnEntities()));
    }

    @Override
    public BowlingTurn[] getTurns() {
        return getBowlingTurn(entity.getTurnEntities());
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        BowlingTurn[] turn=rule.addScores(getBowlingTurn(entity.getTurnEntities()),pins);
        BowlingTurnEntity[] temp=new BowlingTurnEntity[turn.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i]=turn[i].getEntity();
            temp[i].setId(new TurnKeyImpl(i,entity.getId()));
        }
        entity.setTurnEntities(temp);
        return getScores();
    }

    @Override
    public BowlingGameEntity getEntity() {
        return entity;
    }

    BowlingTurn[] getBowlingTurn(BowlingTurnEntity[] turns){
        BowlingTurn[] temp=new BowlingTurn[turns.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i]=new BowlingTurnImpl(turns[i].getFirstPin(),turns[i].getSecondPin());
            temp[i].getEntity().setId(new TurnKeyImpl(turns[i].getId().getId()
                    ,turns[i].getId().getForeignId()));
        }
        return temp;
    }
}
