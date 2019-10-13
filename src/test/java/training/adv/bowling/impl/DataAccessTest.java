package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.why.BowlingGameEntityImpl;
import training.adv.bowling.impl.why.BowlingGameFactoryImpl;
import training.adv.bowling.impl.why.BowlingTurnEntityImpl;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataAccessTest {
	
	private BowlingService bowlingService = new BowlingServiceImpl();
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();

	@Before
	public void before() {
		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
//		System.out.println(ClassLoader.getSystemResource("").getPath());
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
		Connection con=DBUtil.getConnection();
		String sql="select * from game where id=?";
		BowlingGameEntity entity=new BowlingGameEntityImpl();
		try {
			PreparedStatement statement=con.prepareStatement(sql);
			statement.setInt(1,id);
			ResultSet set=statement.executeQuery();
			if (set.next())
				entity.setId(set.getInt("id"));
			else return null;
			return entity;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		//TODO
		Connection con=DBUtil.getConnection();
		String sql="select * from turns where id=? and gameId=?";
		BowlingTurnEntity entity=new BowlingTurnEntityImpl();
		entity.setId(key);
		try {
			PreparedStatement statement=con.prepareStatement(sql);
			statement.setInt(1,key.getId());
			statement.setInt(2,key.getForeignId());
			ResultSet set=statement.executeQuery();
			if (set.next()) {
				entity.setFirstPin(set.getInt("first"));
				entity.setSecondPin(set.getInt("second"));
				if (set.wasNull())entity.setSecondPin(null);
			}else return null;
			return entity;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
