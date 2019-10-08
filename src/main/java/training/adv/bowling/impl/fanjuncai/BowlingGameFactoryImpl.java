package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;

public class BowlingGameFactoryImpl implements BowlingGameFactory{

    @Override
    public BowlingGame getGame() {
        return new BowlingGameImpl(new BowlingRuleImpl());
    }
}