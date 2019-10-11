package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.TurnKey;

public class BowlingTurnKeyImpl implements TurnKey {
    private Integer id, foreignId;

    BowlingTurnKeyImpl(Integer foreignId) {
        this.foreignId = foreignId;
        this.id = UidUtil.getNewTurnId();
    }

    public BowlingTurnKeyImpl(Integer id, Integer foreignId) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BowlingTurnKeyImpl) {
            return ((BowlingTurnKeyImpl) obj).getForeignId().equals(this.getForeignId()) || ((BowlingTurnKeyImpl) obj).getId().equals(this.getId());
        }
        return super.equals(obj);
    }
}
