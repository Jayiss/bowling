package training.adv.bowling.impl.wangbingchao;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.impl.wangbingchao.BowlingGameImpl;
import training.adv.bowling.impl.wangbingchao.BowlingRuleImpl;


public class BowlingGameFactoryImpl implements BowlingGameFactory {
	private BowlingGame bowlingGame;
	@Override
	public BowlingGame getGame() {
		// TODO Auto-generated method stub
		if(bowlingGame != null)
			return bowlingGame;
		else {
			bowlingGame = new BowlingGameImpl(new BowlingRuleImpl(10, 10));
			return bowlingGame;
		}
	}

}
