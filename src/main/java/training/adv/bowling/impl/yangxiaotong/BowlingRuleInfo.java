package training.adv.bowling.impl.yangxiaotong;

import java.util.ArrayList;

import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.dao.impl.yangxiaotong.TurnKeyImpl;

public class BowlingRuleInfo implements BowlingRule{
	protected Integer maxPin;
	protected Integer maxTurn;
	protected Integer id;
	
	public BowlingRuleInfo(Integer id,Integer maxTurn,Integer maxPin) {
		this.maxTurn=maxTurn;
		this.maxPin=maxPin;
		this.id=id;
	}

	public Integer getId() {
		return id;
	}
	

	@Override
	public Boolean isGameFinished(BowlingTurn[] allTurns) {
		// TODO Auto-generated method stub
		Integer length;
		
		if(allTurns!=null) {
			length=allTurns.length;
		}else {
			length=0;
		}		
		
		if((length==maxTurn)&&(isMiss(allTurns[maxTurn-1]))) {
			return true;
		}else if((length==maxTurn+1)&&(isSpare(allTurns[maxTurn-1]))) {
			return true;
		}else if((length==maxTurn+1)&&(isStrike(allTurns[maxTurn-1]))&&(allTurns[maxTurn].getSecondPin()!=null)){
			return true;
		}else if((length==maxTurn+2)&&(isStrike(allTurns[maxTurn-1]))&&(isStrike(allTurns[maxTurn]))) {
			return true;
		}
		return false;
	}

	@Override
	public Integer[] calcScores(BowlingTurn[] allTurns) {
		// TODO Auto-generated method stub
		
		Integer length;
		
		if(allTurns!=null) {
			length=allTurns.length;
		}else {
			length=0;
		}		
		
		/*for(int i=0;i<length;i++) {
			System.out.println(allTurns[i].getFirstPin()+" "+allTurns[i].getSecondPin());
		}*/
		
		if(length==0) {
			return null;
		}else if(length==1) {
			if(isValid(allTurns[0])) {
				Integer[] score=new Integer[1];
				if(allTurns[0].getSecondPin()!=null) {
					score[0]=allTurns[0].getFirstPin()+allTurns[0].getSecondPin();
				}else {
					score[0]=allTurns[0].getFirstPin();
				}
				return score;
			}else {
				return null;
			}
			
		}else {
			Integer[] score;
			if(isGameFinished(allTurns)) {
				score=new Integer[maxTurn];
				for(int i=0;i<maxTurn;i++) {
					if(isValid(allTurns[i])) {
						if(isMiss(allTurns[i])) { //when it is Miss(Against not found pointer)
							score[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
						}else if(isSpare(allTurns[i])) {   //when it is Spare
							score[i]=maxPin+allTurns[i+1].getFirstPin();
						}else if(isStrike(allTurns[i])&&!isStrike(allTurns[i+1])) {  //when it is Strike and the next is not Strike
							score[i]=maxPin+allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin();
						}else if(isStrike(allTurns[i])&&isStrike(allTurns[i+1])) {   //when it is Strike and the next is Strike
							score[i]=maxPin*2+allTurns[i+2].getFirstPin();
						}
					}else {
						return null;
					}
					
				}
			}else {
				score=new Integer[length];
				for(int i=0;i<length-2;i++) {
					if(isValid(allTurns[i])) {
						if(isMiss(allTurns[i])) { //when it is Miss(Against not found pointer)
							score[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
						}else if(isSpare(allTurns[i])) {   //when it is Spare
							score[i]=10+allTurns[i+1].getFirstPin();
						}else if(isStrike(allTurns[i])&&!isStrike(allTurns[i+1])) {  //when it is Strike and the next is not Strike
							score[i]=10+allTurns[i+1].getFirstPin()+allTurns[i+1].getSecondPin();
						}else if(isStrike(allTurns[i])&&isStrike(allTurns[i+1])) {   //when it is Strike and the next is Strike
							score[i]=20+allTurns[i+2].getFirstPin();
						}
					}else {
						return null;
					}
					
				}
				
				if(isValid(allTurns[length-2])) {
					if(isMiss(allTurns[length-2])) {
						score[length-2]=allTurns[length-2].getFirstPin()+allTurns[length-2].getSecondPin();
					}else if(isSpare(allTurns[length-2])) {
						score[length-2]=maxPin+allTurns[length-1].getFirstPin();
					}else {
						if(!isStrike(allTurns[length-1])) {
							if(allTurns[length-1].getSecondPin()!=null) {
								score[length-2]=maxPin+allTurns[length-1].getFirstPin()+allTurns[length-1].getSecondPin();
							}else {
								score[length-2]=maxPin+allTurns[length-1].getFirstPin();
							}
							
						}else {
							score[length-2]=maxPin*2;
						}
					}
				}else {
					return null;
				}
				
				if(isValid(allTurns[length-1])) {
					if(allTurns[length-1].getSecondPin()!=null) {
						score[length-1]=allTurns[length-1].getFirstPin()+allTurns[length-1].getSecondPin();
					}else {
						score[length-1]=allTurns[length-1].getFirstPin();
					}
				}else {
					return null;
				}
				
				
				
			}
			
			return score;
		}
		
		
	}

	@Override
	public Boolean isValid(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if((turn.getFirstPin()<0)||(turn.getFirstPin()>maxPin)) {
			return false;
		}
		if(turn.getSecondPin()!=null) {
			if((turn.getSecondPin()<0)||(turn.getSecondPin()>maxPin)||(turn.getFirstPin()+turn.getSecondPin()>maxPin)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
		// TODO Auto-generated method stub
		
		if(isNewPinsAllowed(existingTurns,pins)) {
			//System.out.println("allowed");
			Integer turnsLength;
			Integer pinsLength;
			
			if(existingTurns!=null) {
				turnsLength=existingTurns.length;
			}else {
				turnsLength=0;
			}
			
			if(pins!=null) {
				pinsLength=pins.length;
			}else {
				pinsLength=0;
			}
			
			ArrayList<BowlingTurn> turns=new ArrayList<BowlingTurn>();
			for(int i=0;i<turnsLength;i++) {
				turns.add(existingTurns[i]);
				//System.out.println(existingTurns[i].getFirstPin()+" "+existingTurns[i].getSecondPin());
			}
			
			
			Integer i=0;
			if((turnsLength!=0)&&(existingTurns[turnsLength-1].getFirstPin()!=maxPin)&&(existingTurns[turnsLength-1].getSecondPin()==null)) {
				
				BowlingTurn test=new BowlingTurnInfo(turns.get(turnsLength-1).getEntity().getFirstPin(),pins[i],turns.get(turnsLength-1).getEntity().getId());
				test.getEntity().setFirstPin(turns.get(turnsLength-1).getEntity().getFirstPin());
				test.getEntity().setSecondPin(pins[i]);
				
				test.getEntity().setId(turns.get(turnsLength-1).getEntity().getId());
				
				turns.remove(turnsLength-1);
				turns.add(test);
				i++;
			}
			
			for(;i<pinsLength;i++) {
				while((i<pinsLength)&&(pins[i]==maxPin)) {
					i++;
					TurnKey key=new TurnKeyImpl(turnsLength+i,id);
					BowlingTurn test1=new BowlingTurnInfo(maxPin,key);
					test1.getEntity().setFirstPin(maxPin);
					
					
					test1.getEntity().setId(key);
					
					turns.add(test1);
				}
				
				if(i==pinsLength-1){
					TurnKey key3=new TurnKeyImpl(turnsLength+i,id);
					BowlingTurn test3=new BowlingTurnInfo(pins[i],key3);
					test3.getEntity().setFirstPin(pins[i]);
					
					
					test3.getEntity().setId(key3);
					
					turns.add(test3);
				}
				
				if(i<pinsLength-1) {
					TurnKey key2=new TurnKeyImpl(turnsLength+i,id);
					
					BowlingTurn test2=new BowlingTurnInfo(pins[i],pins[i+1],key2);
					test2.getEntity().setFirstPin(pins[i]);
					test2.getEntity().setSecondPin(pins[i+1]);
					test2.getEntity().setId(key2);
					
					turns.add(test2);
					i++;
					
				}
				
			}
			
			BowlingTurn[] Turns=(BowlingTurn[]) turns.toArray(new BowlingTurn[turns.size()]);
			
			/*for(int j=0;j<Turns.length;j++) {
				System.out.println(Turns[j].getFirstPin()+" "+Turns[j].getSecondPin());
			}*/
			
			Integer round=Turns.length;
			
			if(round==maxTurn+1) {
				//System.out.println(round);
				
				if((isSpare(Turns[maxTurn-1]))&&(Turns[maxTurn].getSecondPin()==null)) {
					return Turns;
				}else if(isStrike(Turns[maxTurn-1])){
					return Turns;
				}else{
					return existingTurns;
				}
			}
			
			if(round==maxTurn+2) {
				if((isStrike(Turns[maxTurn-1]))&&(isStrike(Turns[maxTurn]))&&(Turns[maxTurn+1].getSecondPin()==null)) {
					return Turns;
				}else {
					return existingTurns;
				}
			}
			
			
			
			return Turns;
		}else {
			return existingTurns;
		}
	}

	@Override
	public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
		// TODO Auto-generated method stub
		Integer turnsLength;
		Integer pinsLength;
		if(existingTurns!=null) {
			turnsLength=existingTurns.length;
		}else {
			turnsLength=0;
		}
		
		if(newPins!=null) {
			pinsLength=newPins.length;
		}else {
			pinsLength=0;
		}
		
		Integer round=turnsLength;
		int i=0;
		if((turnsLength!=0)&&(existingTurns[turnsLength-1].getFirstPin()!=maxPin)&&(existingTurns[turnsLength-1].getSecondPin()==null)) {
			i++;
			if(existingTurns[turnsLength-1].getFirstPin()+newPins[0]>maxPin) {
				return false;
			}
			
		}
		
		for(;i<pinsLength;i++) {
			while((i<pinsLength)&&(newPins[i]==maxPin)) {
				i++;
				round++;
			}
			
			if(i==pinsLength-1) {
				round++;
				if((newPins[i]<0)||(newPins[i]>maxPin)) {
					return false;
				}
				
				/*if(round==maxTurn+1) {
					if((newPins[pinsLength-2]==maxPin)&&(newPins[pinsLength-3]==maxPin)) {
						return true;
					}else if(newPins[pinsLength-2]+newPins[pinsLength-3]==maxPin) {
						return true;
					}else {
						return false;
					}
				}
				
				if(round==maxTurn+2) {
					if((newPins[pinsLength-2]==maxPin)&&(newPins[pinsLength-3]==maxPin)) {
						return true;
					}else {
						return false;
					}
				}*/
			}
			
			if(i<pinsLength-1) {
				BowlingTurn test=new BowlingTurnInfo(newPins[i],newPins[i+1]);
				test.getEntity().setFirstPin(newPins[i]);
				test.getEntity().setSecondPin(newPins[i+1]);
				
				/*System.out.println(newPins[i]);
				System.out.println(newPins[i+1]);
				System.out.println(test.getFirstPin());
				System.out.println(test.getSecondPin());*/
				
				if(!isValid(test)) {
					return false;
				}
				i++;
				round++;
			}
			
			
		}
		
		if(round>maxTurn+2) {
			
			/*if(round==maxTurn+1) {
				if((newPins[turnsLength-3]==maxPin)&&(newPins[turnsLength-4]==maxPin)) {
					return true;
				}else if(newPins[turnsLength-4]+newPins[turnsLength-3]==maxPin) {
					return true;
				}else {
					return false;
				}
			}
			
			if(round==maxTurn+2) {
				if((newPins[turnsLength-3]==maxPin)&&(newPins[turnsLength-4]==maxPin)) {
					return true;
				}else {
					return false;
				}
			}*/
			
			return false;
		}
		return true;
	}

	@Override
	public Boolean isStrike(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if(turn.getFirstPin()==maxPin) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isSpare(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if((turn.getSecondPin()!=null)&&(turn.getFirstPin()+turn.getSecondPin()==maxPin)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isMiss(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if((turn.getSecondPin()!=null)&&(turn.getFirstPin()+turn.getSecondPin()<maxPin)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isFinish(BowlingTurn turn) {
		// TODO Auto-generated method stub
		if((turn.getFirstPin()==maxPin)||(turn.getSecondPin()!=null)) {
			return true;
		}
		return null;
	}

	@Override
	public Integer getMaxPin() {
		// TODO Auto-generated method stub
		return maxPin;
	}

	@Override
	public Integer getMaxTurn() {
		// TODO Auto-generated method stub
		return maxTurn;
	}

}
