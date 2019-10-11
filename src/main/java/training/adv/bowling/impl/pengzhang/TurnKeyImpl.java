package training.adv.bowling.impl.pengzhang;

import training.adv.bowling.api.TurnKey;

import java.util.Objects;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    public TurnKeyImpl(Integer id, Integer foreignId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnKeyImpl turnKey = (TurnKeyImpl) o;
        return id.equals(turnKey.id) &&
                foreignId.equals(turnKey.foreignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foreignId);
    }
}
