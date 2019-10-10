package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

import java.util.UUID;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
   private BowlingGame bowlingGame;
    @Override
    public BowlingGame getGame() {
        if (bowlingGame!=null){
            return bowlingGame;
        }else {
            Integer randomInt = UUID.randomUUID().toString().hashCode();
            if(randomInt<0){
                randomInt = randomInt*(-1);
            }
            System.out.println(randomInt);
            bowlingGame =  new BowlingGameImpl(new BowlingRuleImpl(),randomInt);
            return bowlingGame;
        }
    }
}
