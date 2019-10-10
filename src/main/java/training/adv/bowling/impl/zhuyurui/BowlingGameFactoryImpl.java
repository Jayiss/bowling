package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    private BowlingRule bowlingRule=new BowlingRuleImpl();

    private BowlingGame bowlingGame=new BowlingGameImpl(bowlingRule);

    @Override
    public BowlingGame getGame() {
        return bowlingGame;
    }
}
