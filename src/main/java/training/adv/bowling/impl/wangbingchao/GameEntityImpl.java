package training.adv.bowling.impl.wangbingchao;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;

public class GameEntityImpl implements BowlingGameEntity {
	
	private Integer id;
//	private BowlingTurn[] bowlingTurns= new BowlingTurnImpl[0];
	private BowlingTurnEntity [] turns = new BowlingTurnEntityImpl[0];
	private Integer maxTurn;
	private Integer maxPin;
	
	
	
	public GameEntityImpl(Integer id, Integer maxTurn, Integer maxPin) {
		super();
		this.id = id;
		this.maxTurn = maxTurn;
		this.maxPin = maxPin;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public void setTurnEntities(BowlingTurnEntity[] turns) {
		// TODO Auto-generated method stub
		this.turns = turns;
	}

	@Override
	public BowlingTurnEntity[] getTurnEntities() {
		// TODO Auto-generated method stub
		return turns;
	}

	@Override
	public Integer getMaxTurn() {
		// TODO Auto-generated method stub
		
		
		return maxTurn;
	}

	@Override
	public Integer getMaxPin() {
		// TODO Auto-generated method stub
		
		return maxPin;
	}

}
