package training.adv.bowling.impl.shike;

import java.util.ArrayList;
import java.util.List;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingRuleImpl implements BowlingRule{
	
	private int maxTurn=10;
	private int maxPin=10;
	
	public BowlingRuleImpl(Integer maxTurn, Integer maxPin) {
		// TODO Auto-generated constructor stub
		this.maxPin = maxPin;
		this.maxTurn = maxTurn;
	}

	@Override
	public Boolean isGameFinished(BowlingTurn[] allTurns) {
//		if (allTurns.length>maxTurn+2) {
//			return false;
//		}
//		if (allTurns.length==maxTurn+2 ) {
//			if (allTurns[maxTurn-1].getFirstPin()!=maxPin) {
//				return false;
//			}else {
//				if (allTurns[maxTurn].getFirstPin()<maxPin) {
//					return false;
//				}
//				if (allTurns[maxTurn+1].getFirstPin()<maxPin && allTurns[maxTurn+1].getSecondPin()>0) {
//					return false;
//				}
//			}
//		}
//		if (allTurns.length==maxTurn+1 ) {
//			
//			if (allTurns[maxTurn-1].getFirstPin()!=maxPin && allTurns[maxTurn-1].getFirstPin()+allTurns[maxTurn-1].getSecondPin()!=maxPin) {
//				return false;
//			}else {
//				System.out.println("lengh==maxTurn+1 if2");
//				if (allTurns[maxTurn-1].getFirstPin()==maxPin && allTurns[maxTurn].getFirstPin()<maxPin && allTurns[maxTurn].getSecondPin()==0) {
//						return false;
//					}
//				if (allTurns[maxTurn-1].getFirstPin()<maxPin && allTurns[maxTurn].getFirstPin()<maxPin && allTurns[maxTurn].getSecondPin()!=-1) {
//						return false;
//					}
//				}
//			}
//		return true;
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
//		Integer[] scores = new Integer[allTurns.length];
//		for (int i = 0; i < scores.length; i++) {
//			if (isMiss(allTurns[i])) {
//				scores[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
//			}else if (isSpare(allTurns[i])) {
//				if (i==scores.length-1) {
//					scores[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
//				}else {
//					scores[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin()+allTurns[i+1].getFirstPin();
//				}
//			}else if (isStrike(allTurns[i])) {
//				if (i==scores.length-1) {
//					scores[i]=maxPin;
//				}else if(i==scores.length-2){
//					if (isStrike(allTurns[i+1])) {
//						scores[i]=maxPin+maxPin;
//					}else if(isMiss(allTurns[i+1]) || isSpare(allTurns[i+1])){
//						scores[i]=maxPin+allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin();
//					}else {
//						scores[i]=maxPin+allTurns[i+1].getFirstPin();
//					}
//				}else {
//					if (isStrike(allTurns[i+1])) {
//						scores[i]=maxPin+maxPin+allTurns[i+2].getFirstPin();
//					}else if(isMiss(allTurns[i+1]) || isSpare(allTurns[i+1])){
//						scores[i]=maxPin+allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin();
//					}else {
//						scores[i]=maxPin+allTurns[i+1].getFirstPin();
//					}
//				}
//			}else {
//				scores[i]=allTurns[i].getFirstPin();
//			}
//		}
//		return scores;
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
		if (turn.getFirstPin()+turn.getSecondPin()>maxPin) {
			return false;
		}
		return true;
	}

	@Override
	public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
//		if (isNewPinsAllowed(existingTurns, pins)) {
//			BowlingTurn[] tmpNewPins = addPinsToTurns(existingTurns, pins);
//			return tmpNewPins;
//		}
//		return existingTurns;
		ArrayList<BowlingTurn> list = new ArrayList<BowlingTurn>();		//convert array to List
		for(int i = 0;i < existingTurns.length;i++) 
			list.add(existingTurns[i]);
		if (!isNewPinsAllowed(existingTurns,pins)) {
			System.out.println("new pins not allowed");
			return existingTurns;
		}
			
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
		System.out.println(arr.length);
		return arr;
	}

	@Override
	public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
//		//pins单个是否被允许
//		if(!isEachPinsValid(newPins)) {
//			return false;
//		}
//			
//		//连接在一起
//		BowlingTurn[] tmpNewPins = addPinsToTurns(existingTurns, newPins);
//		//单轮turn是否有效
//		for (int i = 0; i < tmpNewPins.length; i++) {
//			if (!isValid(tmpNewPins[i])) {
//				return false;
//			}
//		}
//		//游戏是否结束
//		if (!isGameFinished(tmpNewPins)) {
//			return false;
//		}
//		return true;
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
//		if (turn.getFirstPin()==maxPin && isFinish(turn)) {
//			return true;
//		}
//		return false;
		if(turn.getFirstPin() == maxPin)
			return true;
		return false;
	}

	@Override
	public Boolean isSpare(BowlingTurn turn) {
//		if (turn.getFirstPin()<maxPin && turn.getFirstPin()+turn.getSecondPin()==maxPin && isFinish(turn)) {
//			return true;
//		}
//		return false;
		if(turn.getFirstPin() == null ||turn.getSecondPin() == null)
			return false;
		if(turn.getFirstPin()+turn.getSecondPin() == maxPin && !isStrike(turn))
			return true;
		return false;
	}

	@Override
	public Boolean isMiss(BowlingTurn turn) {
//		if (turn.getFirstPin()!=maxPin &&turn.getFirstPin()+turn.getSecondPin()<maxPin && isFinish(turn)) {
//			return true;
//		}
//		return false;
		if(!isSpare(turn) && !isStrike(turn))
			return true;
		if(turn.getSecondPin() == null && !isStrike(turn))
			return true;
		return false;
	}

	@Override
	public Boolean isFinish(BowlingTurn turn) {
//		if (turn.getFirstPin() !=maxPin && turn.getSecondPin()!=-1  ) {
//			return true;
//		}
//		if (turn.getFirstPin() ==maxPin) {
//			return true;
//		}
//		System.out.println("not finish");
//		return false;
		if(turn.getSecondPin() != null)
			return true;
		if(isStrike(turn))
			return true;
		
		return false;
	}

	@Override
	public Integer getMaxPin() {
		return maxPin;
	}

	@Override
	public Integer getMaxTurn() {
		return maxTurn;
	}
	
	public Integer[] convergeArray(Integer[] a,Integer[] b) {
	    Integer[] tmp= new Integer[a.length+b.length]; //整合后
	    System.arraycopy(a, 0,tmp, 0, a.length);
	    System.arraycopy(b, 0, tmp, a.length, b.length);
	    return tmp;
	}
	public BowlingTurn[] addPinsToTurns(BowlingTurn[] existingTurns,Integer[] newPins) {
		Integer[] existingPins = bowlingTurnToPins(existingTurns);
		TurnKey[] tks = new TurnKey[existingPins.length];
		for (int i = 0; i < tks.length; i++) {
			tks[i]=existingTurns[i].getEntity().getId();
		}
		TurnKey[] tks2 = new TurnKey[tks.length+newPins.length];
		Integer fid = existingTurns[0].getEntity().getId().getForeignId();
		GetNumber number = new GetNumber();
		for (int i = 0; i < tks2.length; i++) {
			if (i<tks.length) {
				tks2[i]=tks[i];
			}else {
				tks2[i]=new TurnKeyImpl(number.getTurnNum(), fid);
			}
		}
		BowlingTurn[] bts = pinsToBowlingTurns(convergeArray(existingPins, newPins),tks2);
		
		return bts; 
	}
	
	public boolean isEachPinsValid(Integer[] pins) {
		if (pins==null) {
			return false;
		}
		for (int i = 0; i < pins.length; i++) {
			if (pins[i]<0 || pins[i]>maxPin) {
				return false;
			}
		}
		return true;
	}
	//pins[] -> bowlingTurns[] 转换之前先判断pins是否有效
	public BowlingTurn[] pinsToBowlingTurns(Integer[] pins,TurnKey[] tk) { 
		if (pins==null) {
			return new BowlingTurn[0];
		}
		List <BowlingTurn> turnsList = new ArrayList<BowlingTurn>();
		BowlingTurn bTurn = new BowlingTurnImpl(pins);
		
		for (int i = 0; i < tk.length; i++) {
			if (pins[i]==maxPin) {
				bTurn.getEntity().setId(tk[i]);
				bTurn.getEntity().setFirstPin(maxPin);
				bTurn.getEntity().setSecondPin(-1);
				turnsList.add(bTurn);
			}else if(i==pins.length-1){
				bTurn.getEntity().setId(tk[i]);
				bTurn.getEntity().setFirstPin(pins[i]);
				bTurn.getEntity().setSecondPin(-1);
				turnsList.add(bTurn);
			}else {
				bTurn.getEntity().setId(tk[i]);
				bTurn.getEntity().setFirstPin(pins[i]);
				bTurn.getEntity().setSecondPin(pins[i+1]);
				turnsList.add(bTurn);
				i++;
			}
		}
		BowlingTurn[] resultTurns = new BowlingTurn[turnsList.size()];
		for (int i = 0; i < resultTurns.length; i++) {
			resultTurns[i]=turnsList.get(i);
		}
		return resultTurns;
	}
	
//	public BowlingTurn[] pinsToBowlingTurns(Integer[] pins) { 
//		if (pins==null) {
//			return new BowlingTurn[0];
//		}
//		List <BowlingTurn> turnsList = new ArrayList<BowlingTurn>();
//		BowlingTurn bTurn = new BowlingTurnImpl(pins);
//		for (int i = 0; i < pins.length; i++) {
//			if (pins[i]==maxPin) {
//				bTurn.getEntity().setFirstPin(maxPin);
//				bTurn.getEntity().setSecondPin(-1);
//				turnsList.add(bTurn);
//			}else if(i==pins.length-1){
//				bTurn.getEntity().setFirstPin(pins[i]);
//				bTurn.getEntity().setSecondPin(-1);
//				turnsList.add(bTurn);
//			}else {
//				BowlingTurnEntity bte = new BowlingTurnEntityImpl();
//				bTurn.getEntity().setFirstPin(pins[i]);
//				bTurn.getEntity().setSecondPin(pins[i+1]);
//				turnsList.add(bTurn);
//				i++;
//			}
//		}
//		BowlingTurn[] resulTurns = new BowlingTurn[turnsList.size()];
//		for (int i = 0; i < resulTurns.length; i++) {
//			resulTurns[i]=turnsList.get(i);
//		}
//		return resulTurns;
//	}
	
	//bowlingTurns[] -> pins[]
	public Integer[] bowlingTurnToPins(BowlingTurn[] bowlingTurns) {
		if (bowlingTurns==null) {
			System.out.println("111");
			return new Integer[0];
		}
		List <Integer> pinsList = new ArrayList<Integer>();
		for (int i = 0; i < bowlingTurns.length; i++) {
			if (bowlingTurns[i].getSecondPin()==-1) {
				pinsList.add(bowlingTurns[i].getFirstPin());
			}else {
				pinsList.add(bowlingTurns[i].getFirstPin());
				pinsList.add(bowlingTurns[i].getSecondPin());
			}
		}
		Integer[] resulTurns = new Integer[pinsList.size()];
		for (int i = 0; i < resulTurns.length; i++) {
			resulTurns[i]=pinsList.get(i);
		}
		return resulTurns;
	}

}
