package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.TurnKey;

import java.util.Objects;

public class TurnKeyImpl implements TurnKey {

    private Integer id_turn, id_game;  // PK - id_turn, FK - id_game

    public TurnKeyImpl(Integer id_turn, Integer id_game) {
        this.id_turn = id_turn;
        this.id_game = id_game;
    }

    @Override
    public Integer getId() {
        return id_turn;
    }

    @Override
    public Integer getForeignId() {
        return id_game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnKeyImpl turnKey = (TurnKeyImpl) o;
        return Objects.equals(id_turn, turnKey.id_turn) &&
                Objects.equals(id_game, turnKey.id_game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_turn, id_game);
    }
}
