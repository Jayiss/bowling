package training.adv.bowling.impl;

import training.adv.bowling.api.*;

import java.io.Serializable;

public abstract class AbstractGame<T extends Turn, R extends GameRule<T>, E extends GameEntity<? extends Serializable, ? extends TurnEntity>> implements Game<T, R, E> {
	protected R rule;
	public AbstractGame(R rule) {
		this.rule = rule;
	}
}
