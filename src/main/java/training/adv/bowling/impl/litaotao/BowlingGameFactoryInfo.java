package training.adv.bowling.impl.litaotao;
import training.adv.bowling.api.*;

public class BowlingGameFactoryInfo implements BowlingGameFactory{

	private BowlingRuleInfo rule=new BowlingRuleInfo();
	private BowlingGameInfo game=new BowlingGameInfo(rule);
	@Override
	public BowlingGame getGame() {
		// TODO Auto-generated method stub
		return this.game;
	}

}
