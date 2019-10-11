package training.adv.bowling.impl;

import java.util.List;
import java.util.stream.Collectors;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public abstract class AbstractBatchDao extends AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> {
	
	public final List<BowlingTurnEntity> batchLoad(String foreignId) {
		List<BowlingTurnEntity> a = loadAllKey(foreignId).stream().map(this::doLoad).collect(Collectors.toList());
		return loadAllKey(foreignId).stream().map(this::doLoad).collect(Collectors.toList());
	}
	
	public final void batchRemove(String foreignId) {
		loadAllKey(foreignId).stream().forEach(this::remove);
	}
	
	abstract protected List<TurnKey> loadAllKey(String foreignId);

}
