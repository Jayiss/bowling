package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.zhangxinyi.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;


public class DataAccessTest {

    private BowlingService bowlingService = new BowlingServiceImpl();
    private BowlingGameFactory factory = new BowlingGameFactoryImpl();

    @Before
    public void before() {
        String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
        System.out.println(path);
        try (Connection conn = DBUtil.getConnection();
             FileReader fr = new FileReader(new File(path))) {
            RunScript.execute(conn, fr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
        System.out.println(path);
        try (Connection conn = DBUtil.getConnection();
             FileReader fr = new FileReader(new File(path))) {
            RunScript.execute(conn, fr);
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


    // Assemble a BowlingGameEntityImpl based on its id in DB.
    private GameEntity query(Integer id) {
        Integer qTurnMax = null;
        try {
            Connection conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAME WHERE id = '" + id + "'");
            conn.commit();
            System.out.println("id is " + id);
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                qTurnMax = rs.getInt(2);
            }
            System.out.println("Now Empty");
            conn.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (qTurnMax != null) {
            BowlingRule rule = new BowlingRuleImpl();
            GameEntity en = (GameEntity) new BowlingGameImpl(rule).getEntity();
            en.setId(id);
            return en;
        }
        return null;
    }

    private BowlingTurnEntity query(TurnKey key) {
        Integer qId = null;
        Integer qFirstPin = null;
        Integer qSecondPin = null;
        try {
            Connection conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM turn WHERE foreignKey = '" + key.getForeignId() + "'");
            conn.commit();
            System.out.println("id is " + key.getId());
            System.out.println("foreignId is" + key.getForeignId());

            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                System.out.println(rs.getInt(3));
                System.out.println(rs.getInt(4));

                qId = rs.getInt(1);
                qFirstPin = rs.getInt(2);
                qSecondPin = rs.getInt(3);
                if (qSecondPin == -1) {
                    qSecondPin = null;
                }
            }
            System.out.println("Now Empty");
            conn.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BowlingTurnEntity en = new BowlingTurnEntityImpl();
        en.setId(key);
        en.setFirstPin(qFirstPin);
        en.setSecondPin(qSecondPin);
        return en;
    }
}
