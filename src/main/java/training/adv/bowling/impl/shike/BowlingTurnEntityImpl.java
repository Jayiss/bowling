package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity{

	TurnKey id;
	Integer fpin;
	Integer spin;
	
//	public BowlingTurnEntityImpl() {}
//	
//	public BowlingTurnEntityImpl(TurnKey id,Integer fpin,Integer spin) {
//		this.id=id;
//		this.fpin=fpin;
//		this.spin=spin;
//	}
	
	@Override
	public TurnKey getId() {
		return id;
	}

	@Override
	public void setId(TurnKey id) {
		this.id=id;
		
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return fpin;
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return spin;
	}

	@Override
	public void setFirstPin(Integer pin) {
		fpin=pin;
		
	}

	@Override
	public void setSecondPin(Integer pin) {
		spin=pin;
		
	}

}
