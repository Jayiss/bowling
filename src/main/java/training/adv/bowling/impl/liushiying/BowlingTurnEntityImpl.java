package training.adv.bowling.impl.liushiying;

import com.sun.webkit.plugin.PluginListener;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.Turn;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

	private Integer firstPin;
	private Integer secondPin;
	private TurnKey turnKey;

	public BowlingTurnEntityImpl(){
		this(null,null,null);
	}
	public BowlingTurnEntityImpl(Integer firstPin){
		this(firstPin,null,null);
	}
	public BowlingTurnEntityImpl(Integer first,Integer second){
		this(first,second,null);
	}
	public BowlingTurnEntityImpl(Integer first, Integer second, TurnKey turnKey){
		this.firstPin=first;
		this.secondPin=second;
		this.turnKey=turnKey;
	}
	@Override
	public TurnKey getId() {
		return turnKey;
	}

	@Override
	public void setId(TurnKey id) {
		this.turnKey=id;	
	}

	@Override
	public Integer getFirstPin() {
		return firstPin;
	}

	@Override
	public Integer getSecondPin() {
		return secondPin;
	}

	@Override
	public void setFirstPin(Integer pin) {
		this.firstPin=pin;
		
	}

	@Override
	public void setSecondPin(Integer pin) {
		this.secondPin=pin;
		
	}

}
