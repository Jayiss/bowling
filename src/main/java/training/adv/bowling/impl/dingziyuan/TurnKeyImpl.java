package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.TurnKey;

import java.util.Objects;

public class TurnKeyImpl implements TurnKey {
    private Integer tid ;
    private Integer gid ;


    public TurnKeyImpl(Integer tid, Integer gid) {
        this.tid = tid;
        this.gid = gid;
    }

    @Override
    public Integer getId() {
        return tid;
    }

    @Override
    public Integer getForeignId() {
        return gid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnKeyImpl turnKey = (TurnKeyImpl) o;
        return Objects.equals(tid, turnKey.tid) &&
                Objects.equals(gid, turnKey.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, gid);
    }
}
