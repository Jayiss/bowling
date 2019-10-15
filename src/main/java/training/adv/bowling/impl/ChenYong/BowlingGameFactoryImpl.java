package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    @Override
    public BowlingGame getGame() {
        BowlingRuleImpl rule = new BowlingRuleImpl();
        return new BowlingGameImpl(rule);
    }
}
