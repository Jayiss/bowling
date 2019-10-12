package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    static int id=1001;
    @Override
    public BowlingGame getGame() {

        return new BowlingGameImpl(new BowlingRuleImpl(),id++);
    }
}
