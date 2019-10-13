package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.ChenYong.BowlingGameFactoryImpl;
import training.adv.bowling.impl.ChenYong.BowlingGameImpl;
import training.adv.bowling.impl.ChenYong.BowlingRuleImpl;
import training.adv.bowling.impl.ChenYong.BowlingTurnEntityImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DataAccessTest {
	
	private BowlingService bowlingService = new BowlingServiceImpl();
	private BowlingGameFactory factory =  new BowlingGameFactoryImpl();
	
	@Before
	public void before() {
		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
		try {
			path = URLDecoder.decode(path, "UTF-8");
		}catch (Exception e){}
		//path="E://script/setup.sql";
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
		try {
			path = URLDecoder.decode(path, "UTF-8");
		}catch (Exception e){}
		//path="E://script/clean.sql";
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
	
	
	private BowlingGameEntity query(Integer id) {
		//TODO
		//BowlingGameEntity bowlingGameEntity=new BowlingGameImpl(new BowlingRuleImpl());
		String sql="select * from game where id = ?";
		try {
			PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet resultSet=ps.executeQuery();
            resultSet.next();
			int gameScore=resultSet.getInt(2);
			int gameTurn=resultSet.getInt(3);
			int gameId=resultSet.getInt(1);


			BowlingGameEntity bowlingGameEntity=new BowlingGameImpl(new BowlingRuleImpl(),gameScore,gameTurn);
			bowlingGameEntity.setId(gameId);
			return bowlingGameEntity;


		}
		catch (Exception e){
			return null;
		}
		//return new BowlingGameImpl(new BowlingRuleImpl());
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		//TODO
		BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
		String sql="select * from turn where  id = ? and foreignid= ?";
		try {
			PreparedStatement ps = DBUtil.getConnection().prepareStatement(sql);
			ps.setInt(1,key.getId());
			ps.setInt(2,key.getForeignId());
			ResultSet resultSet=ps.executeQuery();
			resultSet.next();
			bowlingTurnEntity.setFirstPin(resultSet.getInt(3));
			bowlingTurnEntity.setSecondPin(resultSet.getInt(4));
			bowlingTurnEntity.setId(key);
		}
		catch (Exception e){}

		return bowlingTurnEntity;
	}
	
}
