package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {

    private static Integer id=1001;
    @Override
    public BowlingGame getGame() {
        return new BowlingGameImpl(BowlingRuleImpl.getInstance(),getId());
    }
    private Integer getId(){
        return id++;
    }

}
