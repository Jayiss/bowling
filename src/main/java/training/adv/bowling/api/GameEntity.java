package training.adv.bowling.api;

public interface GameEntity extends Entity<Integer> {
	void setTurnEntities(TurnEntity[] turns);
	TurnEntity[] getTurnEntities();
	Integer getMaxTurn();
}
