package training.adv.bowling.impl.wangbingchao;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
	
	private TurnKey id;
	private Integer FirstPin;
	private Integer SecondPin;
	@Override
	public TurnKey getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(TurnKey id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return FirstPin;
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return SecondPin;
	}

	@Override
	public void setFirstPin(Integer pin) {
		// TODO Auto-generated method stub
		this.FirstPin = pin;
	}

	@Override
	public void setSecondPin(Integer pin) {
		// TODO Auto-generated method stub
		this.SecondPin = pin;
	}

}
