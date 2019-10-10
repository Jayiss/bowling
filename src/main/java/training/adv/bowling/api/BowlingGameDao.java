package training.adv.bowling.api;


import java.sql.SQLException;

public interface BowlingGameDao{
    void save(BowlingGame domain);
    BowlingGame load(Integer id);
    boolean remove(Integer id);
}
