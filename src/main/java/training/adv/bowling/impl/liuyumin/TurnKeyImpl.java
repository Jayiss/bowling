package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.Turn;
import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private int id;
    private int foreignId;
    public TurnKeyImpl(int id, int foreignId){
        this.id = id;
        this.foreignId = foreignId;
    }
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getForeignId() {
        return this.foreignId;
    }

    @Override
    public boolean equals(Object turnKey) {
        if(((TurnKey)turnKey).getId() == this.id && ((TurnKey)turnKey).getForeignId() == this.foreignId){
            return true;
        } else {
            return false;
        }
    }
}
