package training.adv.bowling.impl.shike;


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
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;




public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {

	Connection conn = null;
	String sql;
	private GetNumber getNum = new GetNumber();

    public BowlingGameDaoImpl(Connection connection) {
    	conn=connection;
	}

	@Override
    protected void doSave(BowlingGameEntity entity) {
		try {
			Statement stmt = conn.createStatement();
			int maxTurn=entity.getMaxTurn();
			int maxPin = entity.getMaxPin();
			int gameNum = getNum.getGameNum();
			entity.setId(gameNum);
			sql="INSERT INTO game (id,max_turn,max_pin) VALUES ("+gameNum+","+maxTurn+","+maxPin+")";
			
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
			
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



    @Override
    protected BowlingGameEntity doLoad(Integer id) {
		ResultSet set=null;
		sql="select * from game where id= "+id;
		boolean flag=false;
		try (Statement stmt = conn.createStatement()){
			set = stmt.executeQuery(sql);

			while (set.next()) {
				BowlingGameEntity entity = new BowlingGameEntityImpl(set.getInt("ID"), set.getInt("MAX_TURN"),set.getInt("MAX_PIN"));

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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }



    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
//        BowlingTurnDao turnDao=new BowlingTurnDaoImpl(conn);
//		List<BowlingTurnEntity> turns = turnDao.batchLoad(entity.getId());
//		BowlingTurnEntity[] turnEntities=turns.toArray(new BowlingTurnEntity[0]);
//		entity.setTurnEntities(turnEntities);
//        BowlingGame bgi = new BowlingGameImpl(entity.getId(), new BowlingRuleImpl(),entity);
//        
//        
//        return bgi; 
    	BowlingGame game = new BowlingGameImpl(entity);
		return game;
    }



    @Override

    public boolean remove(Integer key) {
		sql = "DELETE FROM game WHERE id = "+key;
		try (Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
    }
}
