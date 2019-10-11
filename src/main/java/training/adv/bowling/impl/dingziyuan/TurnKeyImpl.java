package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.TurnKey;
import java.util.Objects;

public class TurnKeyImpl implements TurnKey {
    private String tid;
    private String gid;


    public TurnKeyImpl(String tid, String gid) {
        this.tid = tid;
        this.gid = gid;
    }

    @Override
    public String getId() {
        return tid;
    }

    @Override
    public String getForeignId() {
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
