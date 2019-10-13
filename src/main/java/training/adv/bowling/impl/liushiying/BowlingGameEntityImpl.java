package training.adv.bowling.impl.liushiying;

import training.adv.bowling.api.*;

public class BowlingGameEntityImpl implements BowlingGameEntity {

	private Integer id;
	private BowlingTurnEntity[] turns;
	private Integer maxTurn;
	private Integer maxPin;



	public BowlingGameEntityImpl(Integer id,Integer maxTurn,Integer maxPin){
		this.maxTurn=maxTurn;
		this.maxPin=maxPin;
		this.id=id;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;
	}

	@Override
	public void setTurnEntities(BowlingTurnEntity[] turns) {
		this.turns=turns;
	}

	@Override
	public BowlingTurnEntity[] getTurnEntities() {
		return turns;
	}

	@Override
	public Integer getMaxTurn() {
		return maxTurn;
	}

	@Override
	public Integer getMaxPin() {
		return maxPin;
	}
}
