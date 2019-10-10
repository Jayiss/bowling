package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame {
    private  BowlingTurn[] bowlingTurns;
    private BowlingGameEntity gameEntity;
    public BowlingGameImpl(BowlingRule rule, Integer id) {
        super(rule);
        bowlingTurns = new BowlingTurnImpl[rule.getMaxTurn()+2];
        for(int i=0;i<bowlingTurns.length;i++){
            TurnKey turnKey = new BowlingTurnKeyInfo(i+1,id);
            bowlingTurns[i] = new BowlingTurnImpl(null,null,turnKey);
        }
        this.gameEntity = new BowlingGameInfo(id,rule.getMaxTurn(),rule.getMaxPin());
    }

    public BowlingGameImpl(BowlingRule rule, BowlingTurn[] bowlingTurn, BowlingGameEntity gameEntity){
        super(rule);
        this.bowlingTurns = bowlingTurn;
        this.gameEntity  = gameEntity;
    }
    //get the sum of all score
    @Override
    public Integer getTotalScore() {
        int sum = 0;
        Integer[] scores = rule.calcScores(bowlingTurns);
//        for (Integer score:scores){
//            sum+=score;
//        }
        for (int i=0;i<gameEntity.getMaxTurn();i++){
            sum += scores[i]==null?0:scores[i];
        }
        return sum;
    }

    @Override
    public Integer[] getScores() {
        return rule.calcScores(bowlingTurns);
    }

    @Override
    public BowlingTurn[] getTurns() {
        //在这里截取：
        //需要进行深层复制：
        BowlingTurn[] bowlingTurnsCopy = bowlingTurns.clone();
        for (int i = 0;i<bowlingTurns.length;i++){
            BowlingTurn bowlingTurnCopy = new BowlingTurnImpl(bowlingTurns[i].getFirstPin(),bowlingTurns[i].getSecondPin(),bowlingTurns[i].getEntity().getId());
            bowlingTurnsCopy[i] = bowlingTurnCopy;
        }
        return  bowlingTurnsCopy;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        bowlingTurns = rule.addScores(bowlingTurns,pins);
        return getScores();
    }

    @Override
    public BowlingGameEntity getEntity() {
        List<BowlingTurnInfo> bowlingTurnInfoList = new ArrayList<>();
        int turnID = 1;
        for(BowlingTurn bowlingTurn:bowlingTurns){
                TurnKey turnKey = new BowlingTurnKeyInfo(turnID++,gameEntity.getId());
                BowlingTurnInfo bowlingTurnInfo = new BowlingTurnInfo(bowlingTurn.getFirstPin(),bowlingTurn.getSecondPin(),turnKey);
                bowlingTurnInfoList.add(bowlingTurnInfo);
        }
        BowlingTurnEntity[] turnEntities = new BowlingTurnInfo[bowlingTurnInfoList.size()];
        turnEntities = bowlingTurnInfoList.toArray(turnEntities);
        gameEntity.setTurnEntities(turnEntities);
        return gameEntity;
    }
}
