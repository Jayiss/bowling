package training.adv.bowling.impl;

import org.junit.Test;
import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.caoyu.BowlingGameFactoryImpl;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DefensiveTest {
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();
	
	@Test
	public void testTurnsArrayDefensive() {
		BowlingGame game = factory.getGame();
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(Integer.valueOf(300), game.getTotalScore());
		
		BowlingTurn[] turns = game.getTurns();
		Arrays.fill(turns, null);
		assertEquals(Integer.valueOf(300), game.getTotalScore());
		
		for (BowlingTurn bowlingTurn : game.getTurns()) {
			assertEquals(Integer.valueOf(10), bowlingTurn.getFirstPin());
		}
	}
	
	@Test
	public void testTurnInstanceDefensive() {
		BowlingGame game = factory.getGame();
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(Integer.valueOf(300), game.getTotalScore());
		
		Integer gameId = game.getEntity().getId();
		
		BowlingTurn[] turns = game.getTurns();
		for (BowlingTurn turn : turns) {
			turn.getEntity().setId(new DummyTurnKey());
			turn.getEntity().setFirstPin(5);
			turn.getEntity().setSecondPin(5);
		}

		assertEquals(Integer.valueOf(300), game.getTotalScore());
		
		for (BowlingTurn bowlingTurn : game.getTurns()) {
			assertEquals(gameId, bowlingTurn.getEntity().getId().getForeignId());
			assertEquals(Integer.valueOf(10), bowlingTurn.getFirstPin());
			assertNotEquals(Integer.valueOf(5), bowlingTurn.getSecondPin());
		}
	}
	
	static class DummyTurnKey implements TurnKey {
		private static final long serialVersionUID = 1L;
		@Override
		public Integer getId() {
			return -1;
		}
		@Override
		public Integer getForeignId() {
			return -1;
		}
	}
	
}
