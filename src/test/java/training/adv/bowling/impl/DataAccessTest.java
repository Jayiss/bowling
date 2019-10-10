package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.dingziyuan.BowlingGameFactoryImpl;

import java.util.Arrays;


public class DataAccessTest {

    private BowlingService bowlingService = new BowlingServiceImpl();
    private BowlingGameFactory factory = new BowlingGameFactoryImpl();

    @Before
    public void before() {
    }

    @After
    public void after() {

    }

    @Test
    public void testSave() {
        BowlingGame game = factory.getGame();
        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        bowlingService.save(game);
        GameEntity result = query(game.getEntity().getId());
        assertEquals(game.getEntity().getId(), result.getId());
        assertEquals(game.getEntity().getMaxTurn(), result.getMaxTurn());

        for (BowlingTurn turn : game.getTurns()) {
            BowlingTurnEntity turnEntity = turn.getEntity();
            BowlingTurnEntity turnResult = query(turnEntity.getId());
            assertEquals(turnEntity.getId(), turnResult.getId());
            assertEquals(turnEntity.getFirstPin(), turnResult.getFirstPin());
            assertEquals(turnEntity.getSecondPin(), turnResult.getSecondPin());
        }
    }

    //Prepared data in db.
    @Test
    public void testLoad() {
        BowlingGame game = bowlingService.load(1001);
        GameEntity entity = game.getEntity();

        assertEquals(Integer.valueOf(1001), entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(Integer.valueOf(300), game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() {
        GameEntity before = query(1001);
        assertEquals(Integer.valueOf(1001), before.getId());

        bowlingService.remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }


    private GameEntity query(Integer id) {
        return bowlingService.load(id).getEntity();
    }

    private BowlingTurnEntity query(TurnKey key) {
        BowlingGame game = bowlingService.load(key.getForeignId());
        for (BowlingTurn turn : game.getTurns()) {
            if (turn.getEntity().getId().getId() == key.getId())
                return turn.getEntity();
        }
        return null;
    }

}
