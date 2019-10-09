package training.adv.bowling.impl.liushiying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.media.sound.SoftCubicResampler;
import com.sun.org.apache.bcel.internal.generic.ArrayType;

import sun.tools.jar.SignatureFile;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.Turn;
import training.adv.bowling.api.TurnKey;

public class BowlingRuleImpl implements BowlingRule{

	private Integer curGameID;
	public BowlingRuleImpl(Integer curGameID ) {
		this.curGameID=curGameID;
	}
	
	@Override
	public Boolean isGameFinished(BowlingTurn[] allTurns) {
		int length=allTurns.length;
		if(length<getMaxTurn()){
			return false;
		}
		BowlingTurn lastTurn=allTurns[getMaxTurn()-1];
		if(lastTurn.getSecondPin()==null){
			return false;
		}
		return true;
	}

	//����ÿһ�غϵķ���
	@Override
	public Integer[] calcScores(BowlingTurn[] allTurns) {
		//addScores�Ѿ���֤turn �ĺϷ���
		if(allTurns==null||allTurns.length==0){
			return null;
		}
		int length=allTurns.length;
		Integer[] scores=new Integer[length];
		for(int i=0;i<length;i++){
			if(!isFinish(allTurns[i])){
				scores[i]=allTurns[i].getFirstPin();
				break;
			}else if(i==length-1){
				scores[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
				break;
			}
			
			if(isMiss(allTurns[i])){
				scores[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
			}
			if(isSpare(allTurns[i])){
				scores[i]=getMaxPin()+allTurns[i+1].getFirstPin();
			}
			if(isStrike(allTurns[i])){		
				scores[i]=getMaxPin();
				int count=1;
				int j=i+1;
				
				while(j<=length-1&&isStrike(allTurns[j])&&count<3){
					scores[i]+=getMaxPin();
					j++;
					count++;
				}
				
				if(j<length&&(count<3&&!isFinish(allTurns[j])||count==2)){
					scores[i]+=allTurns[j].getFirstPin();
				}else if(count==1){
					scores[i]+=allTurns[j].getFirstPin()+allTurns[j].getSecondPin();
				}
			}			
		}
		return scores;
	}



	@Override
	public Boolean isValid(BowlingTurn turn) {
		if(turn.getFirstPin()<0||turn.getFirstPin()>10){
			return false;
		}
		if(turn.getSecondPin()==null){
			return true;
		}
		if(turn.getSecondPin()<0||turn.getSecondPin()>10){
			return false;
		}
		if(turn.getFirstPin()+turn.getSecondPin()>10){
			return false;
		}
		return true;
	}

	

	@Override
	public BowlingTurn[] addScores(BowlingTurn[] existingTurns,Integer... pins){
		if(pins==null){
			return existingTurns;
		}
		
		//����pins�Ƿ�Ϸ�
		if(!isNewPinsAllowed(existingTurns, pins)){
			return existingTurns;
		}
		//��־����pins�Ŀ�ʼ����
		int j=0;
		int length=0;
		List<BowlingTurn>turnList=new ArrayList<>();
		if(existingTurns!=null){
			length=existingTurns.length;
			turnList=new ArrayList<>(Arrays.asList(existingTurns));
			BowlingTurn lastTurn=existingTurns[length-1];
			Integer first=lastTurn.getFirstPin();
			Integer second=lastTurn.getSecondPin();
			if(second==null){
				BowlingTurn turn=new BowlingTurnImpl(first, pins[0],lastTurn.getEntity().getId());
				turnList.remove(length-1);
				turnList.add(turn);
				j=1;
			}		
		}
		int count=0;
		for(;j<pins.length;j++){
			BowlingTurn turn;

			TurnKey turnKey=new TurnKeyImpl(length+1+count, curGameID);
			if(j==pins.length-1&&pins[j]!=getMaxPin()){
				turn=new BowlingTurnImpl(pins[j], null, turnKey);
			}else if(pins[j]==getMaxPin()){
				turn=new BowlingTurnImpl(getMaxPin(), 0, turnKey);
			}else{
				turn=new BowlingTurnImpl(pins[j], pins[j++], turnKey);
			}
			turnList.add(turn);	
		}

		//�����������ݳ����Ƿ񳬳�
		if(!testReer(turnList)){
			return existingTurns;
		}
	
		BowlingTurn[] result=new BowlingTurnImpl[turnList.size()];
		for(int i=0;i<turnList.size();i++){
			result[i]=turnList.get(i);
		}
		return result;
	}
	
	//�������ݳ����Ƿ񳬳�Ӧ�õĴ���
	private boolean testReer(List<BowlingTurn> turnList){
		if(turnList.size()>getMaxPin()+2){
			return false;
		}
		BowlingTurn maxTurn;
		BowlingTurn middleTurn;
		BowlingTurn lastTurn;
		int resultLength=turnList.size();
		if(resultLength>getMaxPin()+2){
			return false;
		}
		if(resultLength>getMaxPin()+1){
			maxTurn=turnList.get(getMaxPin()-1);
			middleTurn=turnList.get(getMaxPin());
			lastTurn=turnList.get(getMaxPin()+1);
			if(isMiss(maxTurn)||isSpare(maxTurn)){
				return false;
			}
			if(isStrike(maxTurn)){
				if(!isStrike(middleTurn)){
					return false;
				}
				if(!isStrike(lastTurn)&&lastTurn.getSecondPin()!=null){
					return false;
				}			
			}	
		}
		if(resultLength>getMaxPin()){
			
			maxTurn=turnList.get(getMaxPin()-1);
			middleTurn=turnList.get(getMaxPin());
			if(isMiss(maxTurn)){
				return  false;
			}
			if(isSpare(maxTurn)&&(!isStrike(middleTurn))&&(middleTurn.getSecondPin()!=null)){
				System.out.println("enter");
				return  false;
			}
		}
		return true;
	}
	
	@Override
	public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
		for(int i=0;i<newPins.length;i++){
			if(newPins[i]<0||newPins[i]>getMaxPin()||newPins[i]==null){
				return false;
			}
		}
		int j=0;
		if(existingTurns!=null){
			BowlingTurn lastTurn=existingTurns[existingTurns.length-1];
			Integer first=lastTurn.getFirstPin();
			Integer second=lastTurn.getSecondPin();
			if(second==null&&first+newPins[0]>getMaxPin()){
				return false;
			}
			j=second==null?1:0;
		}
		for(;j<newPins.length-1;j++){
			if(newPins[j]!=getMaxPin()&&newPins[j]+newPins[j++]>getMaxPin()){
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean isStrike(BowlingTurn turn) {
		if(turn.getFirstPin()==getMaxPin()&&turn.getSecondPin()==0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean isSpare(BowlingTurn turn) {
		if(turn.getFirstPin()+turn.getSecondPin()==getMaxPin()&&turn.getFirstPin()!=getMaxPin()){
			return true;
		}
		return false;
	}

	@Override
	public Boolean isMiss(BowlingTurn turn) {
		if(turn.getFirstPin()+turn.getSecondPin()<getMaxPin()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean isFinish(BowlingTurn turn) {
		if(turn.getSecondPin()==null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Integer getMaxPin() {
		return 10;
	}

	@Override
	public Integer getMaxTurn() {
		return 10;
	}

}
