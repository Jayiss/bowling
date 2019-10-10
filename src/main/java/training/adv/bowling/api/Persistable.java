package training.adv.bowling.api;

import java.io.Serializable;

public interface Persistable<E extends Entity<? extends Serializable>> {
	E getEntity();
}
