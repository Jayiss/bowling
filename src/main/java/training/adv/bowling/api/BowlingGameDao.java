package training.adv.bowling.api;

public interface BowlingGameDao{
    void save(BowlingGame domain);
    BowlingGame load(Integer id);
    void remove(Integer id);
}
