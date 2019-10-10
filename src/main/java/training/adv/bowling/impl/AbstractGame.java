package training.adv.bowling.impl;

import training.adv.bowling.api.Game;
import training.adv.bowling.api.GameRule;
import training.adv.bowling.api.Turn;

public abstract class AbstractGame<T extends Turn, R extends GameRule<T>> implements Game<T, R> {
	protected R rule;
	public AbstractGame(R rule) {
		this.rule = rule;
	}
}