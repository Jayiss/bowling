package training.adv.bowling.impl.zhangpeng;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.AbstractGame;
import training.adv.bowling.impl.pengzhang.BowlingGameDaoImpl;
import training.adv.bowling.impl.pengzhang.BowlingTurnDaoImpl;
import training.adv.bowling.impl.pengzhang.TurnKeyImpl;

import java.util.Arrays;


public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGameEntity, BowlingGame {
    private BowlingRule rule;
    private BowlingTurn[] existingTurns;
    private Integer gameId;
    private BowlingTurnEntity[] turnEntities;

    public BowlingGameImpl(BowlingRule rule, Integer gameId) {
        super(rule);
        this.rule = rule;
        this.existingTurns = new BowlingTurn[rule.getMaxTurn() + 3];
        this.gameId = gameId;
    }

    @Override
    public Integer getTotalScore() {
//        for (int i = 0; i < turnEntities.length; i++) {
//            existingTurns[i] = new BowlingGameImpl(new BowlingRuleImpl(turnEntities[i].getMaxTurn(), entity.getMaxPin()),entity.getId());
//        }
        Integer[] eachTurnScore = rule.calcScores(existingTurns);
        Integer result = 0;

        try {
            for (int i = 0; i < eachTurnScore.length; i++) {
                result += eachTurnScore[i];
            }
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    @Override
    public Integer[] getScores() {
        return new Integer[0];
    }

    @Override
    public BowlingTurn[] getTurns() {
        return existingTurns.clone();
    }

    /**
     * 新传入的pins，合法就继续往下算，不合法就全部抛弃
     * 合法的pins会被算成一个个turn，放到存储当前所有turn的list中
     *
     * @param pins 新打出的pins
     * @return 返回值不被关心
     */
    @Override
    public Integer[] addScores(Integer... pins) {
        if (rule.isNewPinsAllowed(existingTurns, pins)) {
            existingTurns = rule.addScores(existingTurns, pins);
        }
        for (int i = 0; i < existingTurns.length; i++) {
            if (existingTurns[i] != null) {
                existingTurns[i].getEntity().setId(new TurnKeyImpl(i, getId()));
            }
        }
        return new Integer[0];
    }

    @Override
    public BowlingGameEntity getEntity() {
//        BowlingGameEntity copyOfThis = new BowlingGameImpl(rule, gameId);
//        copyOfThis.setTurnEntities(getTurnEntities());
//        return copyOfThis;
        return this;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
//        for (int i = 0; i < turns.length; i++) {
//            BowlingTurnEntity test = existingTurns[i].getEntity();
//            test.setFirstPin(0);
//            test.setFirstPin(turns[i].getFirstPin());
////            existingTurns[i].getEntity().setFirstPin(turns[i].getFirstPin());
//            if(turns[i].getSecondPin()!=null){
//                existingTurns[i].getEntity().setSecondPin(turns[i].getSecondPin());
//            }
//        }
        turnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turnEntities;
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
        return rule.getMaxPin();
    }
}
