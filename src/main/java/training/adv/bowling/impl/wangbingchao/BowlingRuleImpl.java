package training.adv.bowling.impl.wangbingchao;

import java.util.ArrayList;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingRuleImpl implements BowlingRule {

	private int maxTurn = 10;
	private int maxPins = 10;
	public BowlingRuleImpl(Integer maxTurn, Integer maxPin) {
		// TODO Auto-generated constructor stub
		this.maxPins = maxPin;
		this.maxTurn = maxTurn;
	}

	@Override
	public Boolean isGameFinished(BowlingTurn[] allTurns) {
		// TODO Auto-generated method stub
		if(allTurns.length == maxTurn && isFinish(allTurns[maxTurn-1]) 
				&& isMiss(allTurns[maxTurn-1])) 
			return true;
		
		if(allTurns.length == maxTurn + 1 && isSpare(allTurns[maxTurn-1])) 
			return true;
		
		if(allTurns.length == maxTurn + 2) 	
			return true;
	
		return false;
	}

	@Override
	public Integer[] calcScores(BowlingTurn[] allTurns) {
		// TODO Auto-generated method stub
		ArrayList<Integer> list = new ArrayList<Integer>();
		if(allTurns.length == 0) {
			Integer[] arr = new Integer[list.size()];
			list.toArray(arr);
			return arr;
		}
		for(int i = 0;i < allTurns.length-1;i++) {
			
			if(isStrike(allTurns[i])) {
				if(i == allTurns.length - 2)
					list.add(allTurns[i].getFirstPin()+allTurns[i+1].getFirstPin()+(allTurns[i+1].getSecondPin()==null?0:allTurns[i+1].getSecondPin()));
				else if(isStrike(allTurns[i+1]))
					list.add(allTurns[i].getFirstPin()+allTurns[i+1].getFirstPin()+allTurns[i+2].getFirstPin());
				else
					list.add(allTurns[i].getFirstPin()+allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin());
			}
			else if(isSpare(allTurns[i]))
				list.add(allTurns[i].getFirstPin()+allTurns[i].getSecondPin()+allTurns[i+1].getFirstPin());
			else
				list.add(allTurns[i].getFirstPin()+allTurns[i].getSecondPin());	
		}
		list.add(allTurns[allTurns.length-1].getFirstPin()
				+(allTurns[allTurns.length-1].getSecondPin()==null?0:allTurns[allTurns.length-1].getSecondPin()));
		Integer[] arr = new Integer[list.size()];
		list.toArray(arr);
		return arr;
	}

	@Override
	public Boolean isValid(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if(turn.getFirstPin()+turn.getSecondPin() > 10)
			return false;
		return true;
	}

	@Override
	public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
		// TODO Auto-generated method stub
		ArrayList<BowlingTurn> list = new ArrayList<BowlingTurn>();		//convert array to List
		for(int i = 0;i < existingTurns.length;i++) 
			list.add(existingTurns[i]);
		if (!isNewPinsAllowed(existingTurns,pins)) 
			return existingTurns;
		for(int i = 0;i < pins.length;i++) {
			if(list.isEmpty()||isFinish(list.get(list.size()-1))) {
				BowlingTurn B = new BowlingTurnImpl(pins[i]);
				
				
				list.add(B);
			}
			else {
				int first = list.get(list.size()-1).getFirstPin();
				BowlingTurn B = new BowlingTurnImpl(first, pins[i]);
				list.set(list.size()-1, B);	
			}
		}
		BowlingTurn[] arr = new BowlingTurn[list.size()];
		list.toArray(arr);
		return arr;
	}

	@Override
	public Integer getMaxTurn() {
		// TODO Auto-generated method stub
		return maxTurn;
	}

	@Override
	public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
		// TODO Auto-generated method stub
		ArrayList<BowlingTurn> list = new ArrayList<BowlingTurn>();		//convert array to List
		for(int i = 0;i < existingTurns.length;i++) 
			list.add(existingTurns[i]);
		for(int i = 0;i < newPins.length;i++) {
			if(newPins[i] > 10 || newPins[i] < 0)
				return false;
			BowlingTurn[] temp = new BowlingTurn[list.size()];		//convert list to array
			list.toArray(temp);
			if(isGameFinished(temp)) 
				return false;	
			if(list.isEmpty()||isFinish(list.get(list.size()-1))){									//the 1st turn immediately add
				BowlingTurn B = new BowlingTurnImpl(newPins[i]);
				list.add(B);
				if(list.size() == maxTurn + 2 && !isStrike(list.get(maxTurn)))
					return false;
			}
			else {
				int first = list.get(list.size()-1).getFirstPin();
				BowlingTurn B = new BowlingTurnImpl(first, newPins[i]);
				if(!isValid(B) || list.size() == maxTurn+2) 
					return false;
				list.set(list.size()-1, B);		
			}
		}
		return true;
	}

	@Override
	public Boolean isStrike(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if(turn.getFirstPin() == maxPins)
			return true;
		return false;
	}

	@Override
	public Boolean isSpare(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if(turn.getFirstPin() == null ||turn.getSecondPin() == null)
			return false;
		if(turn.getFirstPin()+turn.getSecondPin() == maxPins && !isStrike(turn))
			return true;
		return false;
	}

	@Override
	public Boolean isMiss(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if(!isSpare(turn) && !isStrike(turn))
			return true;
		if(turn.getSecondPin() == null && !isStrike(turn))
			return true;
		return false;
	}

	@Override
	public Boolean isFinish(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if(turn.getSecondPin() != null)
			return true;
		if(isStrike(turn))
			return true;
		
		return false;
	}

	@Override
	public Integer getMaxPin() {
		// TODO Auto-generated method stub
		return maxPins;
	}

}


