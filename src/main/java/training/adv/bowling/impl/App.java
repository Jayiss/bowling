package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.impl.liushiying.BowlingGameFactoryImpl;

public class App {

	public static void main(String[] args) {
		BowlingGameFactory factory = BowlingGameFactoryImpl.getInstance(); // TODO: construct your factory here
		BowlingGame game = factory.getGame();
		
		game.addScores(1, 2, 3); // TODO: play your game here
		game.addScores(4, 5, 6); // TODO: play your game here
		// blah blah
		 System.out.println(game.getTotalScore());
	}
	
	

}
