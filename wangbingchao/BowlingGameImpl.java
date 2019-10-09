package wangbingchao;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractGame;
//import training.adv.bowling.impl.game;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule> implements BowlingGame {
//	BowlingRule rule = new BowlingRuleImpl();
	BowlingTurn[] bowlingTurns= new BowlingTurnImpl[0];
	public BowlingGameImpl(BowlingRule rule) {
		super(rule);
	}
	@Override
	public Integer getTotalScore() {
		// TODO Auto-generated method stub
		int sum = 0;
		if(getScores()!=null)
			for(int i = 0;i < getScores().length;i++) {
				if(i == rule.getMaxTurn())
					break;
				sum += getScores()[i];
			}
		return sum;
	}

	@Override
	public Integer[] getScores() {
		// TODO Auto-generated method stub
		return rule.calcScores(bowlingTurns);
	}

	@Override
	public BowlingTurn[] getTurns() {
		// TODO Auto-generated method stub
		return bowlingTurns;
	}

	@Override
	public Integer[] addScores(Integer... pins) {
		// TODO Auto-generated method stub
		
		
		bowlingTurns = rule.addScores(bowlingTurns, pins);
		return pins;
	}

	@Override
	public GameEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

}
