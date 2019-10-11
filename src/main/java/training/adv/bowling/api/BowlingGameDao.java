package training.adv.bowling.api;


import java.sql.SQLException;

public interface BowlingGameDao{
    void save(BowlingGame domain);
    BowlingGame load(String id);
    boolean remove(String id);
}
