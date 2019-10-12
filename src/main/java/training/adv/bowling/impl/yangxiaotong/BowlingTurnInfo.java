package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnInfo implements BowlingTurn{
	Integer firstPin=null;
	Integer secondPin=null;
	TurnKey key=null;
	
	public BowlingTurnInfo(Integer firstPin,Integer secondPin,TurnKey key) {
		//bowlingTurnEntityInfo.setFirstPin(firstPin);
		this.firstPin=firstPin;
		this.secondPin=secondPin;
		this.key=key;
	}
	
	public BowlingTurnInfo(Integer firstPin,TurnKey key) {
		this.firstPin=firstPin;
		this.key=key;
	}
	
	public BowlingTurnInfo(Integer firstPin,Integer secondPin) {
		this.firstPin=firstPin;
		this.secondPin=secondPin;
	}
	
	public BowlingTurnInfo() {
		
	}
	


	@Override
	public BowlingTurnEntity getEntity() {
		// TODO Auto-generated method stub
		BowlingTurnEntity bowlingTurnEntityInfo=new BowlingTurnEntityInfo();
		bowlingTurnEntityInfo.setFirstPin(firstPin);
		bowlingTurnEntityInfo.setSecondPin(secondPin);
		bowlingTurnEntityInfo.setId(key);
		return bowlingTurnEntityInfo;
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
	
	public TurnKey getId() {
		return key;
	}

}
