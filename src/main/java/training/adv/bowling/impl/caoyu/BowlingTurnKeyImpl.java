package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.TurnKey;

public class BowlingTurnKeyImpl implements TurnKey {
    private Integer id, foreignId;

    public BowlingTurnKeyImpl(Integer foreignId) {
        this.foreignId = foreignId;
        this.id = UidUtil.getNewTurnId();
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
