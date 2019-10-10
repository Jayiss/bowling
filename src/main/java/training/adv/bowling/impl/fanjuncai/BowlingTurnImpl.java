package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingTurnImpl implements BowlingTurn {

    private Integer numOfPins;
    private Integer FirstPin;
    private Integer SecondPin;


    private Integer GameId;
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getGameId() {
        return this.GameId;
    }

    public void setGameId(Integer gameId) {
        this.GameId = gameId;
    }
    public BowlingTurnImpl(Integer[] turn) {
        //this.blTurn = turn;
        this.numOfPins = turn.length;
    }

    public Integer getNumOfPins() {
        return numOfPins;
    }

    public void setNumOfPins(Integer numOfPins) {
        this.numOfPins = numOfPins;
    }

    public void setFirstPin(Integer firstPin) {
        FirstPin = firstPin;
    }

    public void setSecondPin(Integer secondPin) {
        SecondPin = secondPin;
    }

    public BowlingTurnImpl(){
        this.numOfPins = null;
        this.FirstPin = null;
        this.SecondPin = null;
        setGameId(Sequence.ID);
        setId(Sequence.TurnTD++);
    }

    @Override
    public Integer getFirstPin() {
        return this.FirstPin;
    }

    @Override
    public Integer getSecondPin() {
        return this.SecondPin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        TurnKeyImpl turnKey = new TurnKeyImpl();
        turnKey.setId(this.Id);
        turnKey.setForeignId(this.GameId);
        BowlingTurnEntityImpl bowlingTurnEntity = new BowlingTurnEntityImpl();
        bowlingTurnEntity.setFirstPin(this.FirstPin);
        bowlingTurnEntity.setSecondPin(this.SecondPin);
        bowlingTurnEntity.setId(turnKey);
        return bowlingTurnEntity;
    }
}
