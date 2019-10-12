package training.adv.bowling.api;

public interface GameRule<T extends Turn> {
//	T newTurn(Integer... scores);

	Boolean isGameFinished(T[] allTurns);

	Integer[] calcScores(T[] allTurns);
	
	Boolean isValid(T turn);
	
	T[] addScores(T[] existingTurns, Integer... pins);
	
	Integer getMaxTurn();
}
