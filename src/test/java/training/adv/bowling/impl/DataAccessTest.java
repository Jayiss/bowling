package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.h2.tools.RunScript;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingService;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.zhuyurui.BowlingGameFactoryImpl;
import training.adv.bowling.impl.zhuyurui.BowlingTurnEntityImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class DataAccessTest {

	private BowlingService bowlingService = new BowlingServiceImpl();
	private BowlingGameFactory factory =  new BowlingGameFactoryImpl();

	@Before
	public void before() {
		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
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
		String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
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
		//TODO
		GameEntity gameEntity=bowlingService.load(id).getEntity();
		if(gameEntity.getId()==null){
			return null;
		}
        return gameEntity;
	}

	private BowlingTurnEntity query(TurnKey key) {
		//TODO
        Connection connection=DBUtil.getConnection();
        BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();


        String sql="Select * from turns where gameId="+key.getForeignId()+" AND id="+key.getId()+";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int i=resultSet.getInt(1);
                bowlingTurnEntity.setId(key);
                bowlingTurnEntity.setFirstPin(resultSet.getInt(3));
                bowlingTurnEntity.setSecondPin(resultSet.getInt(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bowlingTurnEntity;
	}
	
}
