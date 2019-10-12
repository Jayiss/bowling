package training.adv.bowling.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public abstract class AbstractBatchDao extends AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> {
	
	public final List<BowlingTurnEntity> batchLoad(int foreignId) {
//		return loadAllKey(foreignId).stream().map(this::doLoad).collect(Collectors.toList());
		List<BowlingTurnEntity> turns = new ArrayList<>();
		for (TurnKey turnKey : loadAllKey(foreignId)) {
			turns.add(this.doLoad(turnKey));
		}
		return turns;
	}
	
	public final void batchRemove(int foreignId) {
		loadAllKey(foreignId).stream().forEach(this::remove);
	}
	
	abstract protected List<TurnKey> loadAllKey(int foreignId);

}
