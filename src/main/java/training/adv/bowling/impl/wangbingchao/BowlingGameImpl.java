package training.adv.bowling.impl.wangbingchao;

import java.util.ArrayList;


import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;
import training.adv.bowling.impl.AbstractGame;
//import training.adv.bowling.impl.game;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame {
//	BowlingRule rule = new BowlingRuleImpl();
	
	private BowlingGameEntity gameEntity;
//	private BowlingTurn[] bowlingTurns = gameEntity
	public BowlingGameImpl(BowlingRule rule) {
		super(rule);
		gameEntity = new GameEntityImpl(-1, rule.getMaxTurn(), rule.getMaxPin());
	}
	public BowlingGameImpl(BowlingGameEntity entity) {
		
		super(new BowlingRuleImpl(entity.getMaxTurn(), entity.getMaxPin()));
		this.gameEntity = entity;
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
		return rule.calcScores(getTurns());
	}

	@Override
	public BowlingTurn[] getTurns() {
		// TODO Auto-generated method stub
		BowlingTurnEntity[] turns = gameEntity.getTurnEntities();
		BowlingTurn[] bowlingTurns = new BowlingTurn[turns.length];
		// TODO Auto-generated method stub
		for (int i=0;i < turns.length; i++) {
			
			
			
			
			
			if (turns[i].getFirstPin() !=null) {
				if (turns[i].getSecondPin() != null) {
					bowlingTurns[i] = new BowlingTurnImpl(turns[i].getFirstPin(), turns[i].getSecondPin());
				} else {
					bowlingTurns[i] = new BowlingTurnImpl(turns[i].getFirstPin());
				}
			}
			
			bowlingTurns[i].getEntity().setId(turns[i].getId());
			

		}
		return bowlingTurns;
	}

	@Override
	public Integer[] addScores(Integer... pins) {
		// TODO Auto-generated method stub
		BowlingTurn[] bowlingTurns = rule.addScores(getTurns(), pins);
		ArrayList<BowlingTurnEntity> list = new ArrayList<BowlingTurnEntity>();
		for(BowlingTurn turn:bowlingTurns) {
			list.add(turn.getEntity());
		}
		BowlingTurnEntity[] arr = new BowlingTurnEntity[list.size()];
		list.toArray(arr);
		gameEntity.setTurnEntities(arr);
		return pins;
	}

	@Override
	public BowlingGameEntity getEntity() {
		// TODO Auto-generated method stub
		return gameEntity;
	}

}
