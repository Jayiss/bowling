package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey{
	Integer key;
	Integer id;
	
	public TurnKeyImpl(Integer key,Integer id) {
		this.key=key;
		this.id=id;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public Integer getForeignId() {
		// TODO Auto-generated method stub
		return id;
	}

}
