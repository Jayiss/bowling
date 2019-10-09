package wangbingchao;

import java.util.ArrayList;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn {

	ArrayList<Integer> list = new ArrayList<Integer>();
	BowlingTurnImpl(Integer... pins){
		for(int i:pins)
			list.add(i);
	}
	@Override
	public BowlingTurnEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
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
