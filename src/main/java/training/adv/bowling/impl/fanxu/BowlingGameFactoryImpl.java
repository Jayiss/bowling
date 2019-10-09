package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
   private BowlingGame bowlingGame;
    @Override
    public BowlingGame getGame() {
        if (bowlingGame!=null){
            return bowlingGame;
        }else {
            bowlingGame =  new BowlingGameImp(new BowlingRuleImpl());
            return bowlingGame;
        }
    }
}
