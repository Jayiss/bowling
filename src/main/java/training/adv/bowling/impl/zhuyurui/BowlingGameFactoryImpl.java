package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {

    private BowlingGame bowlingGame=new BowlingGameImpl();

    @Override
    public BowlingGame getGame() {
        return bowlingGame;
    }
}
