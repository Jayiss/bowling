package training.adv.bowling.api;

public interface BowlingGameEntity extends GameEntity<String, BowlingTurnEntity> {

    Integer getMaxPin();
}
