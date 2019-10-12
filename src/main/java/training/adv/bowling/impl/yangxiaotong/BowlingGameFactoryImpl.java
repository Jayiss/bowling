package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;

public class BowlingGameFactoryImpl implements BowlingGameFactory{
	
	public static Integer id=0;

	@Override
	public BowlingGame getGame() {
		// TODO Auto-generated method stub
		id++;
		BowlingRuleInfo rule=new BowlingRuleInfo(id,10,10);
		BowlingGame game=new BowlingGameInfo(rule);
		
		game.getEntity().setId(id);
		return game;
	}
	
	public Integer getId() {
		return id;
	}
	

}
