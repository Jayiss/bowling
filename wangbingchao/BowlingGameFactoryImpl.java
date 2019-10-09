package wangbingchao;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import wangbingchao.BowlingGameImpl;
import wangbingchao.BowlingRuleImpl;


public class BowlingGameFactoryImpl implements BowlingGameFactory {
	private BowlingGame bowlingGame;
	@Override
	public BowlingGame getGame() {
		// TODO Auto-generated method stub
		if(bowlingGame != null)
			return bowlingGame;
		else {
			bowlingGame = new BowlingGameImpl(new BowlingRuleImpl());
			return bowlingGame;
		}
	}

}
