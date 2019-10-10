package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
    private  BowlingTurn[] bowlingTurns;
    private  GameEntity gameEntity;
    public BowlingGameImpl(BowlingRule rule,Integer id) {
        super(rule);
        bowlingTurns = new BowlingTurnImpl[rule.getMaxTurn()+2];
        for(int i=0;i<bowlingTurns.length;i++){
            bowlingTurns[i] = new BowlingTurnImpl(null,null);
        }
        this.gameEntity = new BowlingGameInfo(id,rule.getMaxTurn());
    }
    public BowlingGameImpl(BowlingRule rule,BowlingTurn[] bowlingTurn){
        super(rule);
        this.bowlingTurns = bowlingTurn;
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
            sum += scores[i];
        }
        return sum;
    }

    @Override
    public Integer[] getScores() {
        return rule.calcScores(bowlingTurns);
    }

    @Override
    public BowlingTurn[] getTurns() {
        return bowlingTurns;
    }

    @Override
    public Integer[] addScores(Integer... pins) {
        bowlingTurns = rule.addScores(bowlingTurns,pins);
        return getScores();
    }

    @Override
    public GameEntity getEntity() {
        List<BowlingTurnInfo> bowlingTurnInfoList = new ArrayList<>();
        int turnID = 1;
        for(BowlingTurn bowlingTurn:bowlingTurns){
                TurnKey turnKey = new BowlingTurnKeyInfo(turnID++,gameEntity.getId());
                BowlingTurnInfo bowlingTurnInfo = new BowlingTurnInfo(bowlingTurn.getFirstPin(),bowlingTurn.getSecondPin(),turnKey);
                bowlingTurnInfoList.add(bowlingTurnInfo);
        }
        TurnEntity[] turnEntities = new BowlingTurnInfo[bowlingTurnInfoList.size()];
        turnEntities = bowlingTurnInfoList.toArray(turnEntities);
        gameEntity.setTurnEntities(turnEntities);
        return gameEntity;
    }
}
