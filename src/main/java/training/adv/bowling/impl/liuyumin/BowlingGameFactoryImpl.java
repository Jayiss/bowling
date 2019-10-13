package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    static int gameId = 1001;
    @Override
    public BowlingGame getGame() {
        return new BowlingGameImpl(new BowlingRuleImpl(), gameId++);
    }
}
