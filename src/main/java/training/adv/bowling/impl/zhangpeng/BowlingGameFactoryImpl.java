package training.adv.bowling.impl.zhangpeng;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    @Override
    public BowlingGame getGame() {
        int gameId = (int)System.nanoTime();
        int max_turn = 10;
        int max_pin = 10;
        return new BowlingGameImpl(new BowlingRuleImpl(max_turn, max_pin), gameId);
    }
}
