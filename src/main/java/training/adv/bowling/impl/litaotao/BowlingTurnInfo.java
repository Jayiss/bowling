package training.adv.bowling.impl.litaotao;
import training.adv.bowling.api.*;
public class BowlingTurnInfo implements BowlingTurn {
	private BowlingTurnEntityInfo bte=new BowlingTurnEntityInfo();
	
	@Override
	public BowlingTurnEntity getEntity() {
		// TODO Auto-generated method stub
		return bte;
	}
	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return bte.getFirstPin();
	}
	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return bte.getSecondPin();
	}
	
	public void setFirstPin(Integer pin) {
		// TODO Auto-generated method stub
		this.bte.setFirstPin(pin);
	}

	public void setSecondPin(Integer pin) {
		// TODO Auto-generated method stub
		this.bte.setSecondPin(pin);
	}
}
