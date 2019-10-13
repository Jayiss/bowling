package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory{

	private BowlingGame bowlingGame;
	@Override
	public BowlingGame getGame() {
		if(bowlingGame != null)
			return bowlingGame;
		else {
			bowlingGame = new BowlingGameImpl(new BowlingRuleImpl(10, 10));
			return bowlingGame;
		}
	}

}
