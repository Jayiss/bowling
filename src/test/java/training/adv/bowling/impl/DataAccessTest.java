package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.lihaojie.*;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataAccessTest {
	private Connection connection=null;
	private BowlingService bowlingService = new BowlingServiceImpl();
	private BowlingGameFactory factory = new BowlingGameFactoryImpl(); // new BowlingGameFactoryImpl();

	@Before
	public void before() {
//		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
//		System.out.println(path);
//		try (Connection conn = DBUtil.getConnection();
//			 FileReader fr = new FileReader(new File(path))) {
//			RunScript.execute(conn, fr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@After
	public void after() {
		/*String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
		System.out.println(path);
		try (Connection conn = DBUtil.getConnection();
			 FileReader fr = new FileReader(new File(path))) {
			RunScript.execute(conn, fr);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
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
		assertEquals(11, game.getTurns().length);
		assertEquals(Integer.valueOf(300), game.getTotalScore());
	}
	
	//Prepared data in db.
	@Test
	public void testRemove() throws SQLException {
		GameEntity before = query(1001);
		assertEquals(Integer.valueOf(1001), before.getId());
		
		bowlingService.remove(1001);
		
		GameEntity after = query(1001);
		assertNull(after);
	}	
	
	
	private GameEntity query(Integer id) {
		//TODO
		try(Connection connection=DBUtil.getConnection();){
			String sql="select * from game where game_id=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,id);
			ResultSet set= preparedStatement.executeQuery();
			if (set.next()){
				BowlingGameImpl gameEntity=new BowlingGameImpl(new BowlingRuleImpl(),set.getInt(1));
				return gameEntity;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		//TODO
		try(Connection connection=DBUtil.getConnection()) {
			String sql="select *from turn where turn_id=? and game_id=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,key.getId());
			preparedStatement.setInt(2,key.getForeignId());
			ResultSet resultSet=preparedStatement.executeQuery();
			BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
			if (resultSet.next()){
				bowlingTurnEntity.setId(new TurnKeyImpl(resultSet.getInt(1),resultSet.getInt(4)));
				bowlingTurnEntity.setFirstPin(resultSet.getInt(2));
				bowlingTurnEntity.setSecondPin(resultSet.getInt(3));
				return bowlingTurnEntity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
