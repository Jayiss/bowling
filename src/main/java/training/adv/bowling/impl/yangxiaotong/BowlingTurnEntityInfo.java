package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityInfo implements BowlingTurnEntity {

	protected Integer firstPin;
	protected Integer secondPin;
	protected TurnKey id;
	
	@Override
	public TurnKey getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(TurnKey id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return firstPin;
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return secondPin;
	}

	@Override
	public void setFirstPin(Integer pin) {
		// TODO Auto-generated method stub
		this.firstPin=pin;
	}

	@Override
	public void setSecondPin(Integer pin) {
		// TODO Auto-generated method stub
		this.secondPin=pin;
	}

}
