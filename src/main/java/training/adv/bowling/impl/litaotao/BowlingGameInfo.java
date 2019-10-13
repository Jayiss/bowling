package training.adv.bowling.impl.litaotao;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameInfo extends AbstractGame<BowlingTurn, BowlingRule>implements BowlingGame{
	
	Integer totalscore;
	Integer[] scores;
	BowlingTurn[] turn;
	BowlingRuleInfo br=new BowlingRuleInfo();

	public BowlingGameInfo(BowlingRule rule) {
		super(rule);
		this.totalscore=0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Integer[] addScores(Integer... pins) {
		turn=br.addScores(null, pins);
		scores=br.calcScores(turn);
		calculate(scores);
		return scores;
	}
	@Override
	public Integer[] getScores() {
		return scores;
	}
	@Override
	public BowlingTurn[] getTurns() {
		// TODO Auto-generated method stub
		return turn;
	}
	@Override
	public GameEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer getTotalScore() {
		// TODO Auto-generated method stub
		return totalscore;
	}
	
	public void calculate(Integer[] score)
	{
		for(int i=0;i<score.length;i++)
		{
			this.totalscore+=score[i];
		}
	}
}
