package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;


import java.util.ArrayList;

public class BowlingRuleImpl implements BowlingRule {

    private Integer MaxPin = 10;
    private Integer MaxTurn = 10;
    private BowlingTurn[] ExistingTurns = null;

    @Override
    public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
        BowlingTurn existingturn = new BowlingTurnImpl();
        BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
        ArrayList<BowlingTurn> BowlingTurnList = new ArrayList<>();
        int a = 0;
        if (existingTurns == null || existingTurns.length == 0)
            a++;
        else {
            for (BowlingTurn existingTurn : existingTurns)
                BowlingTurnList.add(existingTurn);
        }


        if (newPins == null || newPins.length == 0)
            return false;

        for (Integer newPin : newPins) {

            if (newPin > MaxPin || newPin < 0)
                return false;

            if (BowlingTurnList == null || BowlingTurnList.isEmpty()) {
                bowlingTurn.setNumOfPins(1);
                bowlingTurn.setFirstPin(newPin);
                BowlingTurnList.add(bowlingTurn);
                continue;
            }

            existingturn = BowlingTurnList.get(BowlingTurnList.size() - 1);
            if (existingturn.getSecondPin() == null) {
                Integer existingPin = existingturn.getFirstPin();
                if (existingPin == MaxPin) {
                    bowlingTurn.setNumOfPins(1);
                    bowlingTurn.setFirstPin(newPin);
                    BowlingTurnList.add(bowlingTurn);

                    if (BowlingTurnList.size() > MaxTurn + 2)
                        return false;
                    if (BowlingTurnList.size() == MaxTurn + 2 && BowlingTurnList.get(BowlingTurnList.size() - 3).getFirstPin() != 10)
                        return false;
                    continue;
                }
                else {
                    if ((existingPin + newPin) > MaxPin)
                        return false;
                    else {
                        bowlingTurn.setNumOfPins(2);
                        bowlingTurn.setFirstPin(existingPin);
                        bowlingTurn.setSecondPin(newPin);
                        BowlingTurnList.remove(BowlingTurnList.size() - 1);
                        BowlingTurnList.add(bowlingTurn);
                        if (BowlingTurnList.size() > MaxTurn + 1)
                            return false;
                        else if (BowlingTurnList.size() == MaxTurn + 1) {
                            if (BowlingTurnList.get(BowlingTurnList.size() - 2).getFirstPin() != 10)
                                return false;
                            else
                                continue;
                        }
                        else
                            continue;
                    }

                }
            }
            else {
                bowlingTurn.setNumOfPins(1);
                bowlingTurn.setFirstPin(newPin);
                BowlingTurnList.add(bowlingTurn);
                if(BowlingTurnList.size()>MaxTurn+1)
                    return false;
                else if (BowlingTurnList.size() == MaxTurn + 1) {
                    if (existingturn.getFirstPin() + existingturn.getSecondPin() == MaxPin)
                        continue;
                    else
                        return false;
                }
            }
        }

        return true;
    }

    @Override
    public Boolean isStrike(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isSpare(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isMiss(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isFinish(BowlingTurn turn) {
        return null;
    }

    @Override
    public Boolean isGameFinished(BowlingTurn[] allTurns) {
        return null;
    }

    @Override
    public Integer[] calcScores(BowlingTurn[] allTurns) {
        ArrayList<Integer> Scores = new ArrayList<>();
        if (allTurns == null || allTurns.length == 0) {
            Scores.add(0);
            return Scores.toArray(new Integer[Scores.size()]);
        }

        int count = allTurns.length;
        for (int i = 0; i < count && i < MaxTurn; i++) {

            if (allTurns[i].getFirstPin() == MaxPin) {
                if (i + 1 < count && allTurns[i + 1].getFirstPin() == MaxPin) {
                    if (i + 2 < count)
                        Scores.add(2*MaxPin + allTurns[i + 2].getFirstPin());
                    else
                        Scores.add(2*MaxPin);
                } else if (i + 1 < count && allTurns[i + 1].getFirstPin() != MaxPin) {

                    if (allTurns[i + 1].getSecondPin() == null)
                        Scores.add(MaxPin + allTurns[i + 1].getFirstPin());
                    else
                        Scores.add(MaxPin + allTurns[i + 1].getFirstPin() + allTurns[i + 1].getSecondPin());
                } else
                    Scores.add(MaxPin);
            } else {
                if (allTurns[i].getSecondPin() == null)
                    Scores.add(allTurns[i].getFirstPin());
                else {
                    Integer sum = allTurns[i].getFirstPin() + allTurns[i].getSecondPin();
                    if (sum != MaxPin)
                        Scores.add(sum);
                    else {
                        if (i + 1 < count)
                            Scores.add(sum + allTurns[i + 1].getFirstPin());
                        else
                            Scores.add(sum);
                    }
                }
            }


        }
        return Scores.toArray(new Integer[Scores.size()]);
    }

    @Override
    public Boolean isValid(BowlingTurn turn) {
        return null;
    }

    @Override
    public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {

        ArrayList<BowlingTurn> BowlingTurnList = new ArrayList<>();
        int a = 0;
        if (existingTurns == null || existingTurns.length == 0) {
            if (this.ExistingTurns == null || this.ExistingTurns.length == 0)
                a++;
            else {
                for (BowlingTurn ExistingTurn : this.ExistingTurns)
                    BowlingTurnList.add(ExistingTurn);
            }

        } else {
            if (this.ExistingTurns == null || this.ExistingTurns.length == 0) {
                for (BowlingTurn existingTurn : existingTurns)
                    BowlingTurnList.add(existingTurn);
            } else {
                for (BowlingTurn ExistingTurn : this.ExistingTurns)
                    BowlingTurnList.add(ExistingTurn);
                for (BowlingTurn existingTurn : existingTurns)
                    BowlingTurnList.add(existingTurn);
            }
        }


        if (pins == null || pins.length == 0)
            return null;

        for (Integer newPin : pins) {

            if (newPin > MaxPin || newPin < 0)
                return null;

            if (BowlingTurnList == null || BowlingTurnList.isEmpty()) {
                BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
                bowlingTurn.setNumOfPins(1);
                bowlingTurn.setFirstPin(newPin);
                BowlingTurnList.add(bowlingTurn);
                continue;
            }

            BowlingTurn existingTurn = BowlingTurnList.get(BowlingTurnList.size() - 1);
            if (existingTurn.getSecondPin() == null) {
                Integer existingPin = existingTurn.getFirstPin();
                if (existingPin == MaxPin) {
                    BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
                    bowlingTurn.setNumOfPins(1);
                    bowlingTurn.setFirstPin(newPin);
                    BowlingTurnList.add(bowlingTurn);
                    continue;
                } else {
                    if ((existingPin + newPin) > MaxPin)
                        return null;
                    else {
                        BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
                        bowlingTurn.setNumOfPins(2);
                        bowlingTurn.setFirstPin(existingPin);
                        bowlingTurn.setSecondPin(newPin);
                        BowlingTurnList.remove(BowlingTurnList.size() - 1);
                        BowlingTurnList.add(bowlingTurn);
                        continue;
                    }
                }


            } else {
                BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
                bowlingTurn.setNumOfPins(1);
                bowlingTurn.setFirstPin(newPin);
                BowlingTurnList.add(bowlingTurn);
                continue;
            }
        }

        BowlingTurn[] BLTurn = BowlingTurnList.toArray(new BowlingTurn[BowlingTurnList.size()]);
        this.ExistingTurns = BLTurn;
        return BLTurn;
    }

    @Override
    public Integer getMaxPin() {
        return this.MaxPin;
    }

    @Override
    public Integer getMaxTurn() {
        return this.MaxTurn;
    }

    public void setMaxPin(Integer maxPin) {
        MaxPin = maxPin;
    }

    public void setMaxTurn(Integer maxTurn) {
        MaxTurn = maxTurn;
    }
}
