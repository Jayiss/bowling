package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.Arrays;

public class BowlingRuleImpl implements BowlingRule {
    private static final int MAX_PIN = 10;
    private static final int MAX_TURN = 10;
    @Override
    // 判断游戏是否结束
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        int len=allTurns.length;
        if (len>=MAX_TURN+2) {
            return true;
        } else if (len==MAX_TURN+1&&isSpare(allTurns[MAX_TURN-1])) {
            return true;
        } else if(len==MAX_TURN&&isMiss(allTurns[MAX_TURN-1])){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins){
        int existingTurnsNum = existingTurns.length;
        int unknownTurnsNum = 0;
        int index = 0;
        while (index<newPins.length){
            if (newPins[index] == MAX_PIN||index == newPins.length-1||existingTurnsNum+unknownTurnsNum>=MAX_TURN) {
                BowlingTurn temp = new BowlingTurnImpl(newPins[index]);
                if (isValid(temp)){
                    unknownTurnsNum += 1;
                    index += 1;
                } else{
                    return false;
                }
            } else if (index != newPins.length - 1){
                BowlingTurn temp = new BowlingTurnImpl(newPins[index], newPins[index + 1]);
                if (isValid(temp)){
                    unknownTurnsNum += 1;
                    index += 2;
                } else {
                    return false;
                }
            }
        }
        if(existingTurnsNum+unknownTurnsNum<=MAX_TURN+2)
            return true;
        return false;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn){
        if (isFinish(turn)&&turn.getFirstPin()==MAX_PIN)
            return true;
        return false;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        if (turn.getSecondPin()!=null && isFinish(turn))
            if(turn.getFirstPin()+turn.getSecondPin()==MAX_PIN)
                return true;
        return false;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        if(turn.getSecondPin()!=null&&isFinish(turn)&&(turn.getFirstPin()+turn.getSecondPin()<MAX_PIN))
            return true;
        return false;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        if(turn.getSecondPin()==null&&turn.getFirstPin()<MAX_PIN)
            return false;
        return true;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    // 计算已经存在的轮次的得分
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        int len = allTurns.length;
        int size = len>MAX_TURN?MAX_TURN:len;
        Integer[] scores = new Integer[size];
        for (int i=0;i<size;i++) {
            scores[i] = calcTurn(allTurns[i]);
            if (isSpare(allTurns[i])&&i!=len-1) {
                scores[i]+=allTurns[i+1].getFirstPin();
            }
            if (isStrike(allTurns[i])&&i!=len-1) {
                if (i == len-2) {
                    scores[i] += calcTurn(allTurns[i+1]);
                } else {
                    if (isStrike(allTurns[i+1])) {
                        scores[i] += MAX_PIN + allTurns[i + 2].getFirstPin();
                    } else {
                        if (isFinish(allTurns[i+1])) {
                            scores[i] += calcTurn(allTurns[i+1]);
                        } else {
                            scores[i] += allTurns[i+1].getFirstPin() + allTurns[i+2].getFirstPin();
                        }
                    }
                }
            }
        }
        return scores;
    }

    private int calcTurn(BowlingTurn turn) {
        if (turn.getSecondPin() != null) {
            return turn.getFirstPin() + turn.getSecondPin();
        } else {
            return turn.getFirstPin();
        }
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        if(turn.getSecondPin()==null){
            if(turn.getFirstPin()>=0&&turn.getFirstPin()<=MAX_PIN)
                return true;
            return false;
        }else{
            Integer first = turn.getFirstPin();
            Integer second = turn.getSecondPin();
            if(first>=0&&first<=MAX_PIN&&second>0&&second<=MAX_PIN&&first+second<=MAX_PIN)
                return true;
            return false;
        }
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        int existingTurnsNum = existingTurns.length;
        int index = 0;
        if (existingTurnsNum >= 1) {
            BowlingTurn lastTurn = existingTurns[existingTurnsNum-1];
            if (!isFinish(lastTurn)) {
                existingTurns[existingTurnsNum-1] = new BowlingTurnImpl(lastTurn.getFirstPin(), pins[0]);
                index += 1;
            }
        }
        while (index < pins.length) {
            if (pins[index]==MAX_PIN||index==pins.length-1||existingTurnsNum>=MAX_TURN) {
                BowlingTurn bt = new BowlingTurnImpl(pins[index]);
                existingTurns = addTurn(existingTurns, bt);
                existingTurnsNum += 1;
                index += 1;
            } else if (index!=pins.length-1) {
                BowlingTurn bt = new BowlingTurnImpl(pins[index], pins[index+1]);
                existingTurns = addTurn(existingTurns, bt);
                existingTurnsNum += 1;
                index += 2;
            }
        }
        return existingTurns;
    }
    private BowlingTurn[] addTurn(BowlingTurn[] existingTurns, BowlingTurn newTurn) {
        int len = existingTurns.length;
        BowlingTurn[] newTurns = new BowlingTurn[len + 1];
        System.arraycopy(existingTurns, 0, newTurns, 0, len);
        newTurns[len] = newTurn;
        return newTurns;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}