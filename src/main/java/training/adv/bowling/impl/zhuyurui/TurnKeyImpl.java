package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {

    private int id;
    private int foreignid;

    public TurnKeyImpl(int id,int foreignid){
        this.id=id;
        this.foreignid=foreignid;

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {

        return foreignid;
    }
}
