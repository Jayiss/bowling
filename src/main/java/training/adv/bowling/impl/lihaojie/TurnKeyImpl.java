package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private int id;
    private int foreignId;
    public TurnKeyImpl(int id,int foreignId){
        this.id=id;
        this.foreignId=foreignId;
    }
    @Override
    public Integer getId() {
        return id;

    }

    @Override
    public Integer getForeignId() {
        return foreignId;
    }
    @Override
    public boolean equals(Object k1){

        return this.id==((TurnKey)k1).getId()&&this.foreignId==((TurnKey)k1).getForeignId();

    }
}
