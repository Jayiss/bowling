package training.adv.bowling.impl.why;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {

    private Integer id;
    private Integer fk;
    public TurnKeyImpl(Integer id,Integer fk){
        this.id=id;
        this.fk=fk;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return fk;
    }
}
