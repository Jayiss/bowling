package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.Entity;
import training.adv.bowling.api.Persistable;
import training.adv.bowling.impl.AbstractDao;

import java.io.Serializable;

public class BowlingGameDaoImpl extends AbstractDao implements BowlingGameDao {
    @Override
    public void save(BowlingGame domain) {

    }

    @Override
    public BowlingGame load(Integer id) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    @Override
    protected void doSave(Entity entity) {

    }

    @Override
    protected Entity doLoad(Serializable id) {
        return null;
    }

    @Override
    protected Persistable doBuildDomain(Entity entity) {
        return null;
    }

    @Override
    public boolean remove(Serializable key) {
        return false;
    }
}
