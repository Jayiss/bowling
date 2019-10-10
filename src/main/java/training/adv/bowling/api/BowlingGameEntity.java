package training.adv.bowling.api;

public interface BowlingGameEntity extends GameEntity<Integer, BowlingTurnEntity> {

    Integer getMaxPin();
}
