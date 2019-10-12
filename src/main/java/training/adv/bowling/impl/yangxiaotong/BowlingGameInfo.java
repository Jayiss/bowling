package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.Entity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.GameRule;
import training.adv.bowling.api.Turn;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameInfo extends AbstractGame<BowlingTurn,BowlingRule,BowlingGameEntity> implements BowlingGame{
	
	Integer totalScore;
	Integer[] scores;
	BowlingTurn[] turn;
	//Integer id;
	BowlingRule rule=null;
	BowlingGameEntity gameEntity=null;
	
	@Override
	public BowlingGameEntity getEntity() {
		// TODO Auto-generated method stub
		if(this!=null) {
			gameEntity.setId(rule.getId());
			
			return gameEntity;
		}else return null;
		
	}
	
	
	
	



	public BowlingGameInfo(BowlingRule rule) {
		super(rule);
		this.rule=rule;
		this.totalScore=0;
		turn=null;
		gameEntity=new BowlingGameEntityInfo(rule);

		//this.id=rule.getId();
		//gameEntity=new BowlingGameEntityInfo(rule);

		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer getTotalScore() {
		// TODO Auto-generated method stub
		if(totalScore==0) {
			calcTotalScores(rule.calcScores(turn));
		}		
		return totalScore;
	}

	@Override
	public Integer[] getScores() {
		// TODO Auto-generated method stub
		return rule.calcScores(turn);
	}

	@Override
	public BowlingTurn[] getTurns() {
		// TODO Auto-generated method stub
		if(turn==null) {
			System.out.println("error");
			BowlingTurnEntity[] turns=gameEntity.getTurnEntities();
			turn=new BowlingTurn[turns.length];
			
			for(int i=0;i<turns.length;i++) {
				System.out.println(turns[i].getId().getForeignId()+" "+turns[i].getId().getId()+" "+turns[i].getFirstPin()+" "+turns[i].getSecondPin());
			}
			
			if(turns!=null) {
				for(int i=0;i<turns.length;i++) {
					if(turns[i].getSecondPin()!=null) {
						turn[i]=new BowlingTurnInfo(turns[i].getFirstPin(),turns[i].getSecondPin(),turns[i].getId());
					}else {
						System.out.println(turns[i].getId()+" "+turns[i].getFirstPin());
						turn[i]=new BowlingTurnInfo(turns[i].getFirstPin(),turns[i].getId());
						System.out.println(turns[i].getId().getForeignId()+" "+turns[i].getId().getId()+" "+turns[i].getFirstPin());
					}
				}
				return turn;
			}else {
				return turn;
			}
		}else return turn;
		
		
	}

	@Override
	public Integer[] addScores(Integer... pins) {
		// TODO Auto-generated method stub
		turn=rule.addScores(turn, pins);
		scores=rule.calcScores(turn);
		
		/*for(int i=0;i<turn.length;i++) {
			System.out.println(turn[i].getFirstPin()+" "+turn[i].getSecondPin());
		}*/
		
		totalScore=0;
		calcTotalScores(scores);
		
		return scores;
		
	}

	
	public void calcTotalScores(Integer[] scores) {
		
		Integer length;
		if(scores!=null) {
			length=scores.length;
		}else {
			length=0;
		}
		
		for(int i=0;i<length;i++) {
			this.totalScore+=scores[i];
		}
	}
	

}
