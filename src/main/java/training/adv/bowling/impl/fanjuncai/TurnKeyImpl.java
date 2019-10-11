package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {

    private Integer Id;
    private  Integer ForeignId;

    public void setId(Integer id) {
        this.Id = id;
    }

    public void setForeignId(Integer foreignId) {
        this.ForeignId = foreignId;
    }

    @Override
    public Integer getId() {
        return this.Id;
    }

    @Override
    public Integer getForeignId() {
        return this.ForeignId;
    }
}
