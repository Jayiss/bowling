package training.adv.bowling.impl.shike;

import java.util.ArrayList;
import java.util.List;

import training.adv.bowling.api.*;

import training.adv.bowling.impl.AbstractGame;
import training.adv.bowling.impl.DBUtil;



public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingRule, BowlingGameEntity> implements BowlingGame {

	private int id;
	
	public BowlingGameEntity entity; 

	
	public int maxTurn;
	public int maxPin;
	public Integer[] pins;
	
	public BowlingGameImpl(BowlingRule rule) {
		super(rule);
		entity=new BowlingGameEntityImpl(-1,  rule.getMaxTurn(), rule.getMaxPin());
	}
	
	public BowlingGameImpl(BowlingGameEntity entity) {
		
		super(new BowlingRuleImpl(entity.getMaxTurn(), entity.getMaxPin()));
		this.entity = entity;
	}

//    public BowlingGameImpl(Integer  id,BowlingRule rule,BowlingGameEntity entity) {
//        super(rule);
//        maxTurn=rule.getMaxTurn();
//        maxPin=rule.getMaxPin();
//        this.id=id;
//        brImpl=new BowlingRuleImpl();
//        entity.setTurnEntities(new BowlingTurnEntity[0]);
//        this.entity=entity;
//    }



    @Override
    public Integer getTotalScore() {
		int sum = 0;
		if(getScores()!=null)
			for(int i = 0;i < getScores().length;i++) {
				if(i == rule.getMaxTurn())
					break;
				sum += getScores()[i];
			}
		return sum;
    }



    @Override
    public Integer[] getScores() {
    	return rule.calcScores(getTurns());
    }



    @Override
    public BowlingTurn[] getTurns() {
//    	List<BowlingTurnEntity> bte = new BowlingTurnDaoImpl(DBUtil.getConnection()).batchLoad(id);
    	//BowlingTurn[] bts = brImpl.pinsToBowlingTurns(pins, new TurnKeyImpl(0, id));//’‚ «foreign id
    	BowlingTurnEntity[] turns = entity.getTurnEntities();
    	BowlingTurn[] bts = new BowlingTurnImpl[turns.length];
    	
    	for (int i = 0; i < bts.length; i++) {
			if (turns[i].getFirstPin() !=null) {
				if (turns[i].getSecondPin() != null) {
					bts[i] = new BowlingTurnImpl(turns[i].getFirstPin(), turns[i].getSecondPin());
				} else {
					bts[i] = new BowlingTurnImpl(turns[i].getFirstPin());
				}
			}
			
			bts[i].getEntity().setId(turns[i].getId());
		}
    	return bts;

    }



    @Override
    public Integer[] addScores(Integer... pins) {

    	//BowlingTurn[] bts = this.getTurns();

//    	BowlingTurn[] bts = brImpl.pinsToBowlingTurns(this.pins);
    	//Integer[] existingResult = brImpl.bowlingTurnToPins(bts);
    	
		BowlingTurn[] existingBowlingTurns =getTurns();
		BowlingTurn[] IntegerResult = rule.addScores(existingBowlingTurns, pins);
		
		BowlingTurnEntity[] btes = new BowlingTurnEntity[IntegerResult.length];
		for (int i = 0; i < btes.length; i++) {
			btes[i]=IntegerResult[i].getEntity();
		}
		entity.setTurnEntities(btes);

		return pins;
    }



    @Override
    public BowlingGameEntity getEntity() {
        return entity;
    }

}
