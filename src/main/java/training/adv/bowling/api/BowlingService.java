package training.adv.bowling.api;

import java.sql.SQLException;

public interface BowlingService {
	void save(BowlingGame game);
	BowlingGame load(String id);
	void remove(String id);
}
