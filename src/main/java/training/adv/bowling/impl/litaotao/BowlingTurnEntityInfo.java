package training.adv.bowling.impl.litaotao;
import training.adv.bowling.api.*;
public class BowlingTurnEntityInfo implements BowlingTurnEntity{
	private Integer firstpin;
	private Integer secondpin;
	@Override
	public TurnKey getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(TurnKey id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return this.firstpin;
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return this.secondpin;
	}

	@Override
	public void setFirstPin(Integer pin) {
		// TODO Auto-generated method stub
		this.firstpin=pin;
	}

	@Override
	public void setSecondPin(Integer pin) {
		// TODO Auto-generated method stub
		this.secondpin=pin;
	}

}
