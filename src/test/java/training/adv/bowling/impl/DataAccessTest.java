package training.adv.bowling.impl;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.xushizhi.BowlingGameFactoryImpl;
import training.adv.bowling.impl.xushizhi.BowlingGameImpl;
import training.adv.bowling.impl.xushizhi.BowlingRuleImpl;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DataAccessTest {

    private BowlingService bowlingService = new BowlingServiceImpl();
    private BowlingGameFactory factory = new BowlingGameFactoryImpl();

    @Before
    public void before() {
        String path = "D:/Citi Projects/bowling/src/main/java/training/adv/bowling/impl/xushizhi/resources/scripts/setup.sql";
        // String path = DBUtil.class.getResource("xushizhi/resources/scripts/setup.sql").getPath();
        System.out.println(path);
        try (Connection conn = DBUtil.getConnection();
             FileReader fr = new FileReader(new File(path))) {
            RunScript.execute(conn, fr);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        String path = "D:/Citi Projects/bowling/src/main/java/training/adv/bowling/impl/xushizhi/resources/scripts/clean.sql";
        // String path = DBUtil.class.getResource("xushizhi/resources/scripts/clean.sql").getPath();
        System.out.println(path);
        try (Connection conn = DBUtil.getConnection();
             FileReader fr = new FileReader(new File(path))) {
            RunScript.execute(conn, fr);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        assertEquals(1001, entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(Integer.valueOf(300), game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() {
        GameEntity before = query(1001);
        assertEquals(1001, before.getId());

        bowlingService.remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }

    // Get designated game entity by given id_game
    private GameEntity query(Integer id) {
        Integer maxTurn = null;
        String sql = "Select * From GAME Where id_game = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            conn.commit();

            while (rs.next()) {
                maxTurn = rs.getInt(2);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        // If result game entity is valid
        if (maxTurn != null) {
            GameEntity gameEntity = new BowlingGameImpl(new BowlingRuleImpl()).getEntity();
            gameEntity.setId(id);
            return gameEntity;
        }
        return null;
    }

    // Get designated turn entities by given turn key
    private BowlingTurnEntity query(TurnKey key) {
        BowlingGame bowlingGame = bowlingService.load(key.getForeignId());
        BowlingTurnEntity[] turnEntities = bowlingGame.getEntity().getTurnEntities();
        for (BowlingTurnEntity turnEntity : turnEntities) {
            if (turnEntity.getId().getId().equals((key.getId()))) {
                return turnEntity;
            }
        }
        return null;
    }
}
