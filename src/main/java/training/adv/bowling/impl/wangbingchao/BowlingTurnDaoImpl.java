package training.adv.bowling.impl.wangbingchao;

import java.sql.Connection;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.sun.xml.internal.ws.wsdl.writer.document.Types;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
	private Connection connection;
	private GetNumber getNum = new GetNumber();
	public BowlingTurnDaoImpl(Connection connection) {

		this.connection = connection;
	}



//	@Override
//	public List<BowlingTurnEntity> batchLoad(int id) {
//		// TODO Auto-generated method stub
//		String sql = "SELECT * FROM TURN WHERE FOREIGN_ID = ?";
//		ArrayList<BowlingTurnEntity> list = new ArrayList<BowlingTurnEntity>();
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setInt(1, id);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			while(resultSet.next()) {
//				BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
//				TurnKey keyEntity = new TurnKeyImpl(resultSet.getInt("ID"), resultSet.getInt("FOREIGN_ID"));
//				turnEntity.setId(keyEntity);
//				turnEntity.setFirstPin(resultSet.getInt("FIRST_PIN"));
//				turnEntity.setSecondPin(resultSet.getInt("SECOND_PIN"));
//				list.add(turnEntity);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}

	@Override
	protected List<TurnKey> loadAllKey(int foreignId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM TURN WHERE FOREIGN_ID = ?";
		List<TurnKey> list = new ArrayList<TurnKey>();
		 try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, foreignId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				TurnKey turnKey = new TurnKeyImpl(resultSet.getInt("ID"),resultSet.getInt("FOREIGN_ID"));
				list.add(turnKey);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void doSave(BowlingTurnEntity entity) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO TURN (ID, FOREIGN_ID, FIRST_PIN, SECOND_PIN) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			TurnKey turnkey = entity.getId();
			
			int gameNum = entity.getId().getForeignId();
			int turnNum = getNum.getTurnNum();
			
			preparedStatement.setInt(1, entity.getId().getId());
			preparedStatement.setInt(2, entity.getId().getForeignId());
			
			preparedStatement.setInt(3, entity.getFirstPin());
			
			if(entity.getSecondPin() != null)
				preparedStatement.setInt(4, entity.getSecondPin());
			else
				preparedStatement.setNull(4, Types.INTEGER);
			preparedStatement.execute();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected BowlingTurnEntity doLoad(TurnKey id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM TURN WHERE FOREIGN_ID = ? AND ID = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id.getForeignId());
			preparedStatement.setInt(2, id.getId());
			System.out.print("id="+id.getId());
			System.out.print("fid="+id.getForeignId());
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
				TurnKey keyEntity = new TurnKeyImpl(resultSet.getInt("ID"), resultSet.getInt("FOREIGN_ID"));
				turnEntity.setId(keyEntity);
				turnEntity.setFirstPin(resultSet.getInt("FIRST_PIN"));
				turnEntity.setSecondPin(resultSet.getInt("SECOND_PIN"));
				return turnEntity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(TurnKey key) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "DELETE FROM TURN WHERE FOREIGN_ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, key.getForeignId());
			preparedStatement.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



}
