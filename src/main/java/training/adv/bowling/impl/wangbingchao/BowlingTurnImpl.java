package training.adv.bowling.impl.wangbingchao;

import java.util.ArrayList;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {
	private BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
	ArrayList<Integer> list = new ArrayList<Integer>();
	public BowlingTurnImpl(Integer... pins){
		for(int i:pins)
			list.add(i);
		if(list.size() == 1 || list.size() == 2)
			bowlingTurnEntity.setFirstPin(list.get(0));
		if(list.size() == 2)
			bowlingTurnEntity.setSecondPin(list.get(1));
	}
	@Override
	public BowlingTurnEntity getEntity() {
		if(bowlingTurnEntity != null)
			return bowlingTurnEntity;
		else {
			bowlingTurnEntity = new BowlingTurnEntityImpl();
			return bowlingTurnEntity;
		}
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		if(list.size() != 1 && list.size() != 2)
			return null;
		
		return list.get(0);
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		if(list.size() != 2)
			return null;
		return list.get(1);
	}

}
