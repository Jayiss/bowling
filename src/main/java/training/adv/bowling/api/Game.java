package training.adv.bowling.api;

public interface Game<T extends Turn, R extends GameRule<T>>
		extends Persistable<GameEntity> {
	Integer getTotalScore();
	Integer[] getScores();
	T[] getTurns();

	Integer[] addScores(Integer... pins);
}
