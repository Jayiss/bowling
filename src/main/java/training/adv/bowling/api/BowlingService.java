package training.adv.bowling.api;

import java.sql.SQLException;

public interface BowlingService {
	void save(BowlingGame game);
	BowlingGame load(Integer id);
	void remove(Integer id) throws SQLException;

}
