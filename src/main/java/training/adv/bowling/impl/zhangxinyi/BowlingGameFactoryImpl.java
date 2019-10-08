package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    @Override
    public BowlingGame getGame() {
        BowlingRule rule = new BowlingRuleImpl();
        return new BowlingGameImpl(rule);
    }
}
