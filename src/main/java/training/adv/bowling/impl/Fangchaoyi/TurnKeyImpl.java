package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.Turn;
import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private int id, foreignId;
    public TurnKeyImpl(int id, int foreignId){
        this.id = id;
        this.foreignId = foreignId;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return foreignId;
    }

    public boolean equals(Object a){
        return true;
    }
}
