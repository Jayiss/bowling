package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    // Game Factory --> Bowling Rule --> Bowling Game
    @Override
    public BowlingGame getGame() {
        BowlingRule bowlingRule = new BowlingRuleImpl();
        return new BowlingGameImpl(bowlingRule);
    }
}
