package training.adv.bowling.impl.liushiying;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {
	private BowlingTurnEntity entity;

	public BowlingTurnImpl(BowlingTurnEntity turnEntity){
		this.entity=turnEntity;
	}
	@Override
	public BowlingTurnEntity getEntity() {
		return entity;
	}

	@Override
	public Integer getFirstPin() {
		return this.getEntity().getFirstPin();
	}

	@Override
	public Integer getSecondPin() {
		return this.getEntity().getSecondPin();
	}

}
