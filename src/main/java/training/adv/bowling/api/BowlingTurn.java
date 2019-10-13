package training.adv.bowling.api;

public interface BowlingTurn extends Turn, Persistable<BowlingTurnEntity> {
	Integer getFirstPin();
	Integer getSecondPin();

}
