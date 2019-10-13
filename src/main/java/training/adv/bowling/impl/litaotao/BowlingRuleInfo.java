package training.adv.bowling.impl.litaotao;

import training.adv.bowling.api.*;
import sun.security.util.Length;

public class BowlingRuleInfo implements BowlingRule {
	private int maxpin;
	private int maxturn;

	public BowlingRuleInfo() {
		// TODO Auto-generated constructor stub
		this.maxpin = 10;
		this.maxturn = 10;
	}

	@Override
	public Boolean isGameFinished(BowlingTurn[] allTurns) {// 游戏是否结束
		int gamenum = allTurns.length;
		if (gamenum > 12) {
			return true;
		}
		if (gamenum < maxturn)
			return false;
		if (gamenum == maxturn && this.isMiss(allTurns[maxturn - 1])) {
			return true;
		} 
		if (gamenum == maxturn + 1 && this.isStrike(allTurns[maxturn - 2])
				&& (allTurns[gamenum - 1].getFirstPin() != maxpin)) {
			return true;
		} 
		if (gamenum == maxturn + 2 && (allTurns[gamenum - 3].getFirstPin() == maxpin)
				&& (allTurns[gamenum - 2].getFirstPin() == maxpin)) {

		}
		return false;
	}

	@Override
	public Integer[] calcScores(BowlingTurn[] allTurns) {//
		Integer[] sc;
		if(this.isGameFinished(allTurns))//game is finished
		{
			sc = new Integer[this.maxturn];
			for(int i=0;i<maxturn;i++)
			{
				if(this.isStrike(allTurns[i])&&this.isStrike(allTurns[i+1]))
					sc[i]=20+allTurns[i+2].getFirstPin();
				else if(this.isStrike(allTurns[i])&&!this.isStrike(allTurns[i+1]))
					sc[i]=10+allTurns[i+1].getFirstPin()+allTurns[i+2].getSecondPin();
				else if(this.isSpare(allTurns[i]))
					sc[i]=10+allTurns[i+1].getFirstPin();
				else if(this.isMiss(allTurns[i]))
					sc[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
			}
		}
		else {
			sc = new Integer[allTurns.length];
			for(int i=0;i<allTurns.length-2;i++)
			{
				if(this.isStrike(allTurns[i])&&this.isStrike(allTurns[i+1]))
					sc[i]=this.maxpin*2+allTurns[i+2].getFirstPin();
				else if(this.isStrike(allTurns[i])&&!this.isStrike(allTurns[i+1]))
					sc[i]=this.maxpin+allTurns[i+1].getFirstPin()+allTurns[i+2].getSecondPin();
				else if(this.isSpare(allTurns[i]))
					sc[i]=this.maxpin+allTurns[i+1].getFirstPin();
				else if(this.isMiss(allTurns[i]))
					sc[i]=allTurns[i].getFirstPin()+allTurns[i].getSecondPin();
			}
			if(this.isMiss(allTurns[allTurns.length-2]))
				sc[allTurns.length-2]=allTurns[allTurns.length-2].getFirstPin()+allTurns[allTurns.length-2].getSecondPin();
			else if(this.isSpare(allTurns[allTurns.length-2]))
				sc[allTurns.length-2]=this.maxpin+allTurns[allTurns.length-1].getFirstPin();
			else {
				if(!isStrike(allTurns[allTurns.length-1])) {
					sc[allTurns.length-2]=maxpin+allTurns[allTurns.length-1].getFirstPin()+allTurns[allTurns.length-1].getSecondPin();
				}else {
					sc[allTurns.length-2]=maxpin*2;
				}
			}
		}
		return sc;	
	}

	@Override
	public Boolean isValid(BowlingTurn turn) {
		int f = turn.getFirstPin();
		int s = turn.getSecondPin();
		if (f < 0 || s < 0 || f > maxpin || s > maxpin || f + s > maxpin)
			return false;
		else
			return true;
	}

	@Override
	/**************************************/
	public BowlingTurn[] addScores(BowlingTurn[] existingTurns, Integer... pins) {
		if (!this.isNewPinsAllowed(existingTurns, pins))
			return null;
		int existnum = existingTurns.length;
		BowlingTurnInfo[] bt = new BowlingTurnInfo[12];
		for (int i = 0; i < existnum; i++) {
			bt[i].setFirstPin(existingTurns[i].getFirstPin());
			bt[i].setSecondPin(existingTurns[i].getSecondPin());
		}
		for (int i = 0; i < pins.length;) {
			if (pins[i] == 10) {
				bt[existnum].setFirstPin(pins[i]);
				bt[existnum].setSecondPin(0);
				i++;
				existnum++;
			} else {
				bt[existnum].setFirstPin(pins[i]);
				if (i + 1 < pins.length)
					bt[existnum].setSecondPin(pins[i + 1]);
				if (!this.isValid(bt[existnum]))
					return null;
				else {
					i++;
					i++;
					existnum++;
				}
			}
		}
		return bt;
	}

	@Override
	public Integer getMaxTurn() {
		// TODO Auto-generated method stub
		return this.maxturn;
	}

	@Override
	/**************************************/
	public Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins) {
		if (this.isGameFinished(existingTurns))// 游戏结束了
			return false;
		else {
			for (int i = 0; i < newPins.length; i++) {
				if (newPins[i] < 0)
					return false;
			}
		}
		return true;
	}

	@Override
	public Boolean isStrike(BowlingTurn turn) {
		int f = turn.getFirstPin();
		int s = turn.getSecondPin();
		if (f == 10 && s == 0) {
			return true;
		} else
			return false;
	}

	@Override
	public Boolean isSpare(BowlingTurn turn) {
		int f = turn.getFirstPin();
		int s = turn.getSecondPin();
		if (f + s == 10) {
			return true;
		} else
			return false;
	}

	@Override
	public Boolean isMiss(BowlingTurn turn) {
		int f = turn.getFirstPin();
		int s = turn.getSecondPin();
		if (f + s < 10) {
			return true;
		} else
			return false;
	}

	@Override
	public Boolean isFinish(BowlingTurn turn) {
		if (turn.getFirstPin() >= 0 && turn.getSecondPin() >= 0)
			return true;
		else
			return false;
	}

	@Override
	public Integer getMaxPin() {
		// TODO Auto-generated method stub
		return null;
	}

}
