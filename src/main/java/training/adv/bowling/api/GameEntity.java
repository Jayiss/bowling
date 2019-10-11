package training.adv.bowling.api;

import java.io.Serializable;

public interface GameEntity<K extends Serializable, T extends TurnEntity> extends Entity<K> {
	void setTurnEntities(T[] turns);
	T[] getTurnEntities();
	
	Integer getMaxTurn();
}
