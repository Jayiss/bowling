package training.adv.bowling.impl.zhangpeng;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

import java.util.ArrayList;
import java.util.List;

public class BowlingRuleImpl implements BowlingRule {
    private int MAX_TURN;
    private int MAX_PIN;

    public BowlingRuleImpl(int max_turn, int max_pin) {
        this.MAX_PIN = max_pin;
        this.MAX_TURN = max_turn;
    }

    private boolean isNumberOfPinsRight(int length, int pin1, int pin2, int lengthOfPins, int indexOfPin) {
        if (length == MAX_TURN) {
            if (isSpare(new BowlingTurnImpl(pin1, pin2))) {
                if (lengthOfPins - indexOfPin - 1 > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getLengthOfAllTurns(BowlingTurn[] allTurns) {
        int turnLength = 0;
        for (int i = 0; i < allTurns.length; i++) {
            if (allTurns[i] != null) {
                ++turnLength;
            }
        }
        return turnLength;
    }

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        if (newPins.length == 0) {
            return false;
        }

        int lengthOfTurns = getLengthOfAllTurns(existingTurns);
        BowlingTurn lastTurn = getLastTurn(existingTurns);
        if (isFinish(lastTurn)) {
            for (int i = 0; i < newPins.length; i++) {
                if (newPins[i].equals(MAX_PIN)) {
                    ++lengthOfTurns;
                } else if (i != newPins.length - 1) {
                    if (!isValid(new BowlingTurnImpl(newPins[i], newPins[++i]))) {
                        return false;
                    }
                    ++lengthOfTurns;
                    if (isNumberOfPinsRight(lengthOfTurns, newPins[i - 1], newPins[i], newPins.length, i)) {
                        continue;
                    }
                    return false;
                } else {
                    if (!isValid(new BowlingTurnImpl(newPins[i], 0))) {
                        return false;
                    }
                }
            }
        } else {
            if (!isValid(new BowlingTurnImpl(lastTurn.getFirstPin(), newPins[0]))) {
                return false;
            }
            if (lengthOfTurns == MAX_TURN) {
                if (isSpare(new BowlingTurnImpl(lastTurn.getFirstPin(), newPins[0]))) {
                    if (newPins.length - 1 > 1) {
                        return false;
                    }
                }
            }
            for (int i = 1; i < newPins.length; i++) {
                if (isNumberOfPinsRight(lengthOfTurns, newPins[i - 1], newPins[i], newPins.length, i)) {
                    continue;
                }
                if (newPins[i].equals(MAX_PIN)) {
                    ++lengthOfTurns;
                    continue;
                }
                if (i != newPins.length - 1) {
                    if (!isValid(new BowlingTurnImpl(newPins[i], newPins[++i]))) {
                        return false;
                    }
                    ++lengthOfTurns;
                    //核对剩余的pins数量是否合理，isNumberOfPinsRight需要传的参数过多，重新写
                    if (lengthOfTurns == MAX_TURN) {
                        if (isSpare(new BowlingTurnImpl(newPins[i - 1], newPins[i]))) {
                            if (newPins.length - i - 1 > 1) {
                                return false;
                            }
                        }
                    }
                } else {
                    if (!isValid(new BowlingTurnImpl(newPins[i], null))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        try {
            return turn.getFirstPin() == MAX_PIN;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        try {
            return turn.getFirstPin() + turn.getSecondPin() == MAX_PIN;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        try {
            return turn.getFirstPin() + turn.getSecondPin() < MAX_PIN;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        if (turn == null) {
            return true;
        }
        if (turn.getFirstPin() == MAX_PIN) {
            return true;
        } else if (turn.getSecondPin() != null) {
            return true;
        }

        return false;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    /**
     * 基于给定的turns判断game是否结束
     * <p>
     * 如果turn的length < maxTurn，未结束
     * 关键看第maxTurn这一轮打出的分
     * <p>
     * 如果是strike，那就能多打两次，如果多打的第一次仍是strike，就再加一轮，就是maxTurn+2轮。
     * 如果多打的第一次不是strike，就只多加一轮
     * <p>
     * 如果是spare，多加一次，也就一轮
     * <p>
     * 如果是miss，不加
     *
     * @param allTurns 当前game的turns
     * @return boolean true 表示game结束，false反之
     */
    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        int lengthOfTurns = allTurns.length;
        if (lengthOfTurns == MAX_TURN + 2) {
            return true;
        }
        //如果当前打的轮次超过了max_turn，处于加球阶段
        if (lengthOfTurns >= getMaxTurn()) {
            if (isStrike(allTurns[getMaxTurn() - 1])) {
                if (lengthOfTurns == MAX_TURN + 1) {
                    return !isStrike(allTurns[getMaxTurn()]);
                } else {
                    return false;
                }
            }
            if (isSpare(allTurns[getMaxTurn() - 1])) {
                return lengthOfTurns == 11;
            }
        }
        return allTurns.length == getMaxTurn();
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        int turnLength = getLengthOfAllTurns(allTurns);
        Integer[] scoreOfEachTurn = new Integer[turnLength];

        for (int i = 0; i < turnLength; i++) {
            if (isStrike(allTurns[i])) {
                if (i == turnLength - 1 && i < MAX_TURN) {
                    scoreOfEachTurn[i] = MAX_PIN;
                }
                if (i == turnLength - 2 && i < MAX_TURN) {
                    scoreOfEachTurn[i] = MAX_PIN + allTurns[i + 1].getFirstPin();
                    if (allTurns[i + 1].getSecondPin() != null) {
                        scoreOfEachTurn[i] += allTurns[i + 1].getSecondPin();
                    }
                } else if (i < turnLength - 2) {
                    scoreOfEachTurn[i] = isStrike(allTurns[i + 1]) ? (MAX_PIN + MAX_PIN + allTurns[i + 2].getFirstPin()) : (MAX_PIN + allTurns[i + 1].getFirstPin() + allTurns[i + 1].getSecondPin());
                    scoreOfEachTurn[i] = isStrike(allTurns[i + 1]) ? (MAX_PIN + MAX_PIN + allTurns[i + 2].getFirstPin()) : (MAX_PIN + allTurns[i + 1].getFirstPin() + allTurns[i + 1].getSecondPin());
                }
            } else {
                if (i == turnLength - 1) {
                    if (turnLength <= MAX_TURN) {//加打的本身不算分
                        scoreOfEachTurn[i] = allTurns[i].getFirstPin();
                        if (allTurns[i].getSecondPin() != null) {
                            scoreOfEachTurn[i] += allTurns[i].getSecondPin();
                        }
                    }
                } else {
                    //result = spare ? spare : miss
                    scoreOfEachTurn[i] = isSpare(allTurns[i]) ? (MAX_PIN + allTurns[i + 1].getFirstPin()) : (allTurns[i].getFirstPin() + allTurns[i].getSecondPin());
                }
            }
        }
        return scoreOfEachTurn;
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        boolean isValid = false;
        if (turn.getFirstPin() == MAX_PIN) {
            if (turn.getSecondPin() != null) {
                isValid = false;
            }
        } else {
            if (turn.getFirstPin() < 0) {
                isValid = false;
            }
            if (turn.getSecondPin() != null) {
                if (turn.getFirstPin() < 0) {
                    isValid = false;
                } else if (turn.getFirstPin() + turn.getFirstPin() <= MAX_PIN) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    private BowlingTurn getLastTurn(BowlingTurn[] existingTurns) {
        int indexOfLastTurn = 0;
        if (existingTurns[0] != null) {
            for (int i = 0; i < existingTurns.length; i++) {
                if (existingTurns[i] != null) {
                    indexOfLastTurn = i;
                } else break;
            }
        }
        return existingTurns[indexOfLastTurn];
    }

    /**
     * 用List拷贝当前进行过的轮次，然后尝试利用新的pins做成新的轮次，
     * 若检测到game结束而pins还有剩余，舍弃所有pins返回之前的轮次，这应该是isNewPinsAllowed做的工作
     *
     * @param existingTurns 目前进行过的轮次
     * @param pins          新打出的成绩
     * @return 把pins和existingTurns合并之后的turn
     */
    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
        BowlingTurn lastTurn = getLastTurn(existingTurns);
        List<BowlingTurn> existingTurnsTemp = new ArrayList<>();
        for (int i = 0; i < existingTurns.length; i++) {
            if (existingTurns[i] != null) {
                existingTurnsTemp.add(existingTurns[i]);
            }
        }

        int indexOfPins = 0;
        if (!isFinish(lastTurn)) {
            existingTurnsTemp.remove(existingTurnsTemp.size() - 1);
            existingTurnsTemp.add(new BowlingTurnImpl(lastTurn.getFirstPin(), pins[0]));
            indexOfPins = 1;
        }
        for (int i = indexOfPins; i < pins.length; i++) {
            if (isGameFinished(existingTurnsTemp.toArray(new BowlingTurn[existingTurnsTemp.size()]))) {
                return existingTurns;
            }
            if (pins[i].equals(MAX_PIN) || i == pins.length - 1 || existingTurnsTemp.size() == MAX_TURN + 1) {
                existingTurnsTemp.add(new BowlingTurnImpl(pins[i], null));
            } else {
                existingTurnsTemp.add(new BowlingTurnImpl(pins[i], pins[++i]));
            }
        }
        return existingTurnsTemp.toArray(new BowlingTurn[existingTurnsTemp.size()]);
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}
