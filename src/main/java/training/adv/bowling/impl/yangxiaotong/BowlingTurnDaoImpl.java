package training.adv.bowling.impl.yangxiaotong;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.Entity;
import training.adv.bowling.api.Persistable;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;
import training.adv.bowling.impl.yangxiaotong.BowlingTurnEntityInfo;
import training.adv.bowling.impl.yangxiaotong.BowlingTurnInfo;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao{
	
	private Connection connection;
	
    public BowlingTurnDaoImpl(Connection connection){
        this.connection = connection;
    }


	@Override
	public void save(BowlingTurn turn) throws SQLException{
		doSave(turn.getEntity());
	}

	@Override
	public void doSave(BowlingTurnEntity entity) throws SQLException {
		// TODO Auto-generated method stub
		Statement st=connection.createStatement();
		String sql;
		if(entity.getSecondPin()!=null) {
			sql="insert into bowlingTurn(id,turnId,firstPin,secondPin) values("+entity.getId().getForeignId()+","+entity.getId().getId()+","+entity.getFirstPin()+","+entity.getSecondPin()+")";
			
		}else {
			sql="insert into bowlingTurn(id,turnId,firstPin) values("+entity.getId().getForeignId()+","+entity.getId().getId()+","+entity.getFirstPin()+")";
		
		}
		
		st.executeUpdate(sql);
	}

	@Override
	protected List<TurnKey> loadAllKey(int foreignId) {
		// TODO Auto-generated method stub
		List<TurnKey> l = new ArrayList<TurnKey>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select turnId from bowlingTurn where id="+foreignId);
            while(rs.next()){
                Integer id = rs.getInt(1);
                TurnKey key = new TurnKeyImpl(id,foreignId);
                l.add(key);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return l;
	}

	@Override
	protected BowlingTurnEntity doLoad(TurnKey id) {
		// TODO Auto-generated method stub
		Integer foreignId = id.getForeignId();
        Integer Id =id.getId();
        BowlingTurnEntity bte = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bowlingTurn where id="+foreignId+" and turnId="+Id);
            if(rs.next()) {
                bte=new BowlingTurnEntityInfo();
                int firstPin = rs.getInt("firstPin");
                Integer secondPin = null;
                if (!(rs.getString("secondPin") == null))
                    secondPin = rs.getInt("secondPin");
                TurnKey key = new TurnKeyImpl(Id, foreignId);
                bte.setFirstPin(firstPin);
                bte.setSecondPin(secondPin);
                bte.setId(key);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bte;
	}

	@Override
	protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
		// TODO Auto-generated method stub
		Integer first = entity.getFirstPin();
        Integer second = entity.getSecondPin();
        TurnKey key=entity.getId();
        BowlingTurn bowlingTurn = new BowlingTurnInfo(first,second,key);
        return bowlingTurn;
	}

	@Override
	public boolean remove(TurnKey key) {
		// TODO Auto-generated method stub
		int foreignId = key.getForeignId();
        int id = key.getId();
        int flag = 0;
        try {
            Statement stmt = connection.createStatement();
            flag = stmt.executeUpdate("delete from bowlingTurn where id="+foreignId+" and turnId="+id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return flag>0?true:false;
	}


	@Override
	public List<BowlingTurnEntity> batchLoad(Integer id) {
		// TODO Auto-generated method stub
		return loadAllKey(id).stream().map(this::doLoad).collect(Collectors.toList());
	}


	@Override
	public void batchRemove(Integer id) {
		// TODO Auto-generated method stub
		loadAllKey(id).stream().forEach(this::remove);
	}



	

}
