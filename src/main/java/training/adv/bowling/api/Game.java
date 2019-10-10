package training.adv.bowling.api;

import java.io.Serializable;

public interface Game<T extends Turn, R extends GameRule<T>, E extends Entity<? extends Serializable>>
		extends Persistable<E> {
	Integer getTotalScore();
	Integer[] getScores();
	T[] getTurns();
	
	Integer[] addScores(Integer... pins);
}
