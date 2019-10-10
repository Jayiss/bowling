package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.GameEntity;

import java.util.Date;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
   private BowlingGame bowlingGame;
    @Override
    public BowlingGame getGame() {
        if (bowlingGame!=null){
            return bowlingGame;
        }else {
            Integer randomInt =  new Long(new Date().getTime()).intValue();
//            GameEntity gameEntity = new BowlingGameInfo();
//            gameEntity.setId(randomInt);
            bowlingGame =  new BowlingGameImpl(new BowlingRuleImpl(),randomInt);
            return bowlingGame;
        }
    }
}
