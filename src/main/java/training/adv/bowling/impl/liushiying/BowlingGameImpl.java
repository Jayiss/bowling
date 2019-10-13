package training.adv.bowling.impl.liushiying;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn,BowlingRule,BowlingGameEntity> implements BowlingGame {


	private BowlingGameEntity bowlingGameEntity;
	private BowlingTurn[] bowlingTurns;


	public BowlingGameImpl(BowlingRule rule) {
		this(rule,null);
	}


	public BowlingGameImpl(BowlingRule rule,BowlingGameEntity bowlingGameEntity) {
		super(rule);
		this.rule=rule;
		this.bowlingGameEntity=bowlingGameEntity;
	}

	private void injectTurn(){
		BowlingTurnEntity[] turnEntities=this.getEntity().getTurnEntities();
		if(turnEntities==null||turnEntities.length==0){
			return;
		}
		bowlingTurns=new BowlingTurn[turnEntities.length];
		for(int i=0;i<turnEntities.length;i++) {
			bowlingTurns[i] = new BowlingTurnImpl(turnEntities[i]);
		}
	}

	
	
	@Override
	public Integer getTotalScore() {
		injectTurn();
		int sum=0;
		Integer[]scores=getScores();
		if(scores==null){
			return 0;
		}
		if(!rule.isGameFinished(bowlingTurns)){
			for(Integer s:scores){
				//System.out.println(s);
				sum+=s;
			}
		}else{
			for(int i=0;i<rule.getMaxTurn();i++){
				System.out.println("分数："+scores[i]);
				sum+=scores[i];
			}
		}
		return sum;
	}

	//每回合分数
	@Override
	public Integer[] getScores() {
		return rule.calcScores(bowlingTurns);
		
	}

	//回合对象
	@Override
	public BowlingTurn[] getTurns() {
		BowlingTurnEntity[] turnEntity= this.getEntity().getTurnEntities();
		BowlingTurn[] returnTurns=new BowlingTurnImpl[turnEntity.length];
		BowlingTurnEntity[] returnEntites=new BowlingTurnEntity[turnEntity.length];
		for(int i=0;i<turnEntity.length;i++){
			returnEntites[i]=new BowlingTurnEntityImpl(turnEntity[i].getFirstPin(),turnEntity[i].getSecondPin(),turnEntity[i].getId());
			returnTurns[i]=new BowlingTurnImpl(returnEntites[i]);
		}
		return returnTurns;
	}

	@Override
	public Integer[] addScores(Integer... pins) {
		if(pins==null||pins.length==0){
			return getScores();
		}
		injectTurn();
		bowlingTurns=rule.addScores(bowlingTurns, pins);
		/*System.out.print("当前游戏为："+id);
		System.out.println("列数："+bowlingTurns.length);
		for(BowlingTurn turn:bowlingTurns){
			System.out.println(turn.getFirstPin()+" : "+turn.getSecondPin());
		}*/
		if(bowlingTurns==null||bowlingTurns.length==0){
			return getScores();
		}

		Integer id=this.getEntity().getId();
		BowlingTurnEntity[] turnEntities=new BowlingTurnEntity[bowlingTurns.length];
		for(int i=0;i<bowlingTurns.length;i++){
			turnEntities[i]=bowlingTurns[i].getEntity();
			turnEntities[i].setId(new TurnKeyImpl(i,id));
		}
		bowlingGameEntity.setTurnEntities(turnEntities);
		return getScores();

	}

	@Override
	public BowlingGameEntity getEntity() {
		return bowlingGameEntity;
	}

}
