package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {

    @Override
    public BowlingGame getGame() {
        return new BowlingGameImpl(new BowlingRuleImpl());
    }
}
