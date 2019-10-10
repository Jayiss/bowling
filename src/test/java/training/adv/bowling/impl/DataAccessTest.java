package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.caokeke.*;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

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
	public void testASave() {
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
	public void testBLoad() {
		BowlingGame game = bowlingService.load(1001);
		GameEntity entity = game.getEntity();
		assertEquals(Integer.valueOf(1001), entity.getId());
		assertEquals(Integer.valueOf(10), entity.getMaxTurn());
		assertEquals(12, game.getTurns().length);
		assertEquals(Integer.valueOf(300), game.getTotalScore());
	}
	
	//Prepared data in db.
	@Test
	public void testCRemove() {
		GameEntity before = query(1001);
		assertEquals(Integer.valueOf(1001), before.getId());
		
		bowlingService.remove(1001);
		
		GameEntity after = query(1001);
		assertNull(after);
	}	
	
	
	private GameEntity query(Integer id) {
		BowlingGame bg=bowlingService.load(id);
		if(bg==null)return null;
		return bg.getEntity();
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		Connection connection=DBUtil.getConnection();
		BowlingTurnEntity res=null;
		Statement st = null;
		try{
			st = connection.createStatement();
			String sql = "SELECT * FROM turns WHERE id="+key.getId()+
					" AND foreignId="+key.getForeignId();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next())
				res=new BowlingTurnEntityImpl(
								rs.getInt("firstPin"),
								rs.getInt("secondPin"),
								rs.getInt("id"),
								rs.getInt("foreignId"));
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	
}
