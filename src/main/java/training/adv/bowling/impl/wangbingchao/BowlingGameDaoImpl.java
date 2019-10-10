package training.adv.bowling.impl.wangbingchao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {

	private Connection connection;
	
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();
	private GetNumber getNum = new GetNumber();
	private BowlingTurnDao bowlingTurnDao ;
	public BowlingGameDaoImpl(Connection connection) {
		
		this.connection = connection;
		bowlingTurnDao = new BowlingTurnDaoImpl(connection);
	}

	@Override
	protected void doSave(BowlingGameEntity entity) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO GAME (ID, MAX_PIN, MAX_TURN) VALUES (?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int gameNum = getNum.getGameNum();
			entity.setId(gameNum);
			
			BowlingTurnEntity [] turns = new BowlingTurnEntity[entity.getTurnEntities().length];
			
			for(int i = 0;i < turns.length;i++)
			{
				
				turns[i] = new BowlingTurnEntityImpl();
				TurnKey turnKey = new TurnKeyImpl(i+1,gameNum);
				turns[i].setId(turnKey);
				turns[i].setFirstPin(entity.getTurnEntities()[i].getFirstPin());
				if(entity.getTurnEntities()[i].getSecondPin()!= null)
					turns[i].setSecondPin(entity.getTurnEntities()[i].getSecondPin());
				
			}
			entity.setTurnEntities(turns);
			
			preparedStatement.setInt(1, entity.getId());
			preparedStatement.setInt(2, entity.getMaxPin());
			preparedStatement.setInt(3, entity.getMaxTurn());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected BowlingGameEntity doLoad(Integer id) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "SELECT * FROM GAME WHERE ID = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				BowlingGameEntity entity = new GameEntityImpl(resultSet.getInt("ID"), resultSet.getInt("MAX_TURN"),resultSet.getInt("MAX_PIN"));
//				entity.setId(resultSet.getInt("ID"));
//				entity.setId(resultS);
				BowlingTurnEntity[] list = entity.getTurnEntities();
				ArrayList<BowlingTurnEntity> bowlingTurns= new ArrayList<BowlingTurnEntity>();
				for(BowlingTurnEntity turn: list) {
					
					BowlingTurn bowlingTurn = new BowlingTurnImpl();
					bowlingTurn.getEntity().setId(turn.getId());
					bowlingTurn.getEntity().setFirstPin(turn.getFirstPin());
					bowlingTurn.getEntity().setSecondPin(turn.getSecondPin());
					
				}
				return entity;
			}
			
			
//			BowlingTurnEntity[] arr = new BowlingTurnEntity[list.size()];
//			list.toArray(arr);
//			game.getEntity().setTurnEntities(arr);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
		// TODO Auto-generated method stub
		BowlingGame game = new BowlingGameImpl(entity);
		
		return game;
	}

	@Override
	public boolean remove(Integer key) {
		try {
			String sql = "DELETE FROM GAME WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, key);
			preparedStatement.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	

}
