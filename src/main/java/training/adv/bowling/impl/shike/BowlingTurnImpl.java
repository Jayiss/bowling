package training.adv.bowling.impl.shike;

import java.util.ArrayList;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn{
	private BowlingTurnEntity bte=new BowlingTurnEntityImpl();
	ArrayList<Integer> list = new ArrayList<Integer>();
	
	public BowlingTurnImpl(Integer... pins) {
		for(int i:pins)
			list.add(i);
		if(list.size() == 1 || list.size() == 2)
			bte.setFirstPin(list.get(0));
		if(list.size() == 2)
			bte.setSecondPin(list.get(1));
	}
	
	@Override
	public BowlingTurnEntity getEntity() {
		if(bte != null) {
//			BowlingTurnEntity bte2 = new BowlingTurnEntityImpl();
//			TurnKey tk = new TurnKeyImpl(bte.getId().getId(),bte.getId().getForeignId());
//			bte2.setId(tk);
//			bte2.setFirstPin(bte.getFirstPin());
//			bte2.setSecondPin(bte.getSecondPin());
//			return bte2;
			return bte;
		}
		else {
			bte = new BowlingTurnEntityImpl();
			return bte;
		}
	}

	@Override
	public Integer getFirstPin() {
		if(list.size() != 1 && list.size() != 2)
			return null;
		
		return list.get(0);
	}

	@Override
	public Integer getSecondPin() {
		if(list.size() != 2)
			return null;
		return list.get(1);
	}

}
