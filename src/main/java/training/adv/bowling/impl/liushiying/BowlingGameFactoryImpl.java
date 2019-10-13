package training.adv.bowling.impl.liushiying;

import java.util.Random;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;

public class BowlingGameFactoryImpl implements BowlingGameFactory{

	private static BowlingGameFactoryImpl factory=new BowlingGameFactoryImpl();
	private Integer index=1002;
	
	private BowlingGameFactoryImpl(){
		
	}
	
	public static BowlingGameFactoryImpl getInstance(){
		return factory;
	}
	
	@Override
	public BowlingGame getGame() {
		//TODO:改成可扩展性
		BowlingRule rule=BowlingRuleImpl.getInstance();
		BowlingGameImpl bowlingGameImpl=new BowlingGameImpl(rule,new BowlingGameEntityImpl(index,rule.getMaxTurn(),rule.getMaxPin()));
		index++;
		return bowlingGameImpl;
	}

}
