package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.Fangchaoyi.BowlingGameDaoImpl;
import training.adv.bowling.impl.Fangchaoyi.BowlingGameFactoryImpl;
import training.adv.bowling.impl.Fangchaoyi.BowlingTurnDaoImpl;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.List;


public class DataAccessTest {
	
	private BowlingService bowlingService = new BowlingServiceImpl();
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();
	
	@Before
	public void before() {
		String path = ClassLoader.getSystemResource("script/create.sql").getPath();
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
		String path = ClassLoader.getSystemResource("script/drop.sql").getPath();
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
		Connection connection = DBUtil.getConnection();
		BowlingGameDao gameDao = new BowlingGameDaoImpl(connection);
		BowlingTurnDao turnDao = new BowlingTurnDaoImpl(connection);
		BowlingGame game = gameDao.load(id);
		BowlingGameEntity entity = game.getEntity();
		List<BowlingTurnEntity> turns = turnDao.batchLoad(id);
		if(turns.isEmpty()) return null;
		entity.setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
		if(entity.getTurnEntities() == null) return null;
		return entity;
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		//TODO
		Connection connection = DBUtil.getConnection();
		BowlingTurnDao turnDao = new BowlingTurnDaoImpl(connection);
		List<BowlingTurnEntity> turnEntities = turnDao.batchLoad(key.getForeignId());
		for(BowlingTurnEntity entity : turnEntities){
			if(entity.getId().getId() == key.getId()) return entity;
		}
		return null;
	}
	
}
