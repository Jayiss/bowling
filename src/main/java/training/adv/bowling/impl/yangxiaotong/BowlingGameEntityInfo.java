package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnEntity;

public class BowlingGameEntityInfo implements BowlingGameEntity{
	BowlingTurnEntity[] turns;
	Integer maxTurn;
	Integer id;
	Integer maxPin;
	//Integer totalScore
	
	BowlingRule rule;
	//BowlingGameInfo bowlingGame=new BowlingGameInfo(rule);
	Integer[] scores;
	
	public BowlingGameEntityInfo(BowlingRule rule) {
		//bowlingGame=new BowlingGameInfo(rule);
		this.rule=rule;
		//this.totalScore=0;
		turns=null;
		this.id=rule.getId();
		this.maxTurn=rule.getMaxTurn();
		this.maxPin=rule.getMaxPin();
	}

	@Override
	public void setTurnEntities(BowlingTurnEntity[] turns) {
		// TODO Auto-generated method stub
		this.turns=turns;
	}

	@Override
	public BowlingTurnEntity[] getTurnEntities() {
		// TODO Auto-generated method stub
		return turns;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public Integer getMaxPin() {
		// TODO Auto-generated method stub
		return maxPin;
	}

	@Override
	public Integer getMaxTurn() {
		// TODO Auto-generated method stub
		return maxTurn;
	}

}
