package training.adv.bowling.api;

public interface BowlingRule extends GameRule<BowlingTurn> {
	Boolean isNewPinsAllowed(BowlingTurn[] existingTurns, Integer[] newPins);
	
	Boolean isStrike(BowlingTurn turn);
	Boolean isSpare(BowlingTurn turn);
	Boolean isMiss(BowlingTurn turn);
	
	Boolean isFinish(BowlingTurn turn);
	
	Integer getMaxPin();
	Integer getMaxTurn();
}
