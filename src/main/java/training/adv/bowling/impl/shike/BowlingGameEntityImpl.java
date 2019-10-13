package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity{

	private int id;
	private BowlingTurnEntity[] btes=new BowlingTurnEntityImpl[0];
	private Integer maxTurn;
	private Integer maxPin;
	
	public BowlingGameEntityImpl(Integer id, Integer maxTurn, Integer maxPin) {
		super();
		this.id = id;
		this.maxTurn = maxTurn;
		this.maxPin = maxPin;
	}
	
	@Override
	public void setTurnEntities(BowlingTurnEntity[] turns) {
		this.btes=turns;
	}

	@Override
	public BowlingTurnEntity[] getTurnEntities() {
		return btes;
	}
	@Override
	public Integer getId() {return this.id;}

	@Override
	public void setId(Integer id) {this.id=id;}

	@Override
	public Integer getMaxPin() {
		return maxPin;
	}
	
	@Override
	public Integer getMaxTurn() {
		return maxTurn;
	}

}
