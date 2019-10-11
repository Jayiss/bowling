package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;

import java.util.concurrent.atomic.AtomicInteger;

public class BowlingGameFactoryImpl implements BowlingGameFactory {

    private AtomicInteger id = new AtomicInteger(0);

    @Override
    public BowlingGame getGame() {
        BowlingRule rule = new BowlingRuleImpl();
        return new BowlingGameImpl(id.getAndIncrement(), rule);
    }
}
