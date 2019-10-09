package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {

    public BowlingGameFactoryImpl(){}
    @Override
    public BowlingGame getGame()
    {
        return new BowlingGameImpl();
    }
}
