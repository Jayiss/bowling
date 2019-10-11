package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private Integer id;
    private Integer maxTurn;
    private Integer maxPin;
    private List<BowlingTurnEntity> turnEntities;
    public BowlingGameEntityImpl(Integer id, Integer maxTurn,Integer maxPin){
        this.id = id;
        this.maxTurn = maxTurn;
        this.maxPin = maxPin;
        this.turnEntities = new ArrayList<BowlingTurnEntity>();
        //this.turnEntities = turnEntities;
    }


    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        turnEntities = Arrays.asList(turns);
    }


    @Override
    public BowlingTurnEntity[] getTurnEntities() {

        return turnEntities.toArray(new BowlingTurnEntity[turnEntities.size()]);
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }
}
