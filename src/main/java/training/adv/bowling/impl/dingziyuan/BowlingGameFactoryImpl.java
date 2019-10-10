package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    @Override
    public BowlingGame getGame() {
        return new BowlingGameImpl(new BowlingRuleImpl(10,10));
    }
}
