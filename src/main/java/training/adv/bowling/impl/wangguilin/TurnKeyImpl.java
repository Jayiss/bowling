package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;
    TurnKeyImpl(Integer id,Integer foreignId){
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
}
