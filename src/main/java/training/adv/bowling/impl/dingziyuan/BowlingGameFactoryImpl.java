package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    @Override
    public BowlingGame getGame() {
        BowlingRuleImpl bowlingRule = new BowlingRuleImpl(10,10);
        return new BowlingGameImpl(bowlingRule);
    }
}
