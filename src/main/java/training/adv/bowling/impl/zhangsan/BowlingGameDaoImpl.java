package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.AbstractDao;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {

    @Override
    protected void doSave(BowlingGameEntity entity) {
        
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();

        return null;
    }

    @Override
    public boolean remove(Integer key) {
        return false;
    }
}
