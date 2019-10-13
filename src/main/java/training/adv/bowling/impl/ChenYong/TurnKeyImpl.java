package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    Integer id;
    Integer foreignId;

    public TurnKeyImpl(Integer id,Integer foreignId)
    {
        this.id=id;
        this.foreignId=foreignId;
        //foreignId=getForeignId();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return foreignId;
    }
}
