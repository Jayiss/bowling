package training.adv.bowling.dao.impl.yangxiaotong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.Game;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;
import training.adv.bowling.impl.yangxiaotong.BowlingGameEntityInfo;
import training.adv.bowling.impl.yangxiaotong.BowlingGameInfo;
import training.adv.bowling.impl.yangxiaotong.BowlingRuleInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity,BowlingGame,Integer> 
                                implements BowlingGameDao {

    private Connection connection;
    //BowlingRule rule=new BowlingRuleInfo();
    //GameEntity entity=new BowlingGameEntityInfo(rule);
    
    public BowlingGameDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) throws SQLException {
    	
    	Statement stmt=connection.createStatement();
		String sql="insert into bowlingGame(id,maxTurn,maxPin) values("+entity.getId()+","+entity.getMaxTurn()+","+entity.getMaxPin()+")";
		stmt.executeUpdate(sql);
    	
        /*Statement stmt = connection.createStatement();
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        stmt.executeUpdate("insert into game(id,maxTurn) values("+id+",maxTurn)");*/
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id){
        /*GameEntity ge = null;
        BowlingRule rule=null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bowlingGame where id="+id);
            Integer maxTurn=rs.getInt("maxTurn");
            Integer maxPin=rs.getInt("maxPin");
            rule=new BowlingRuleInfo(id,maxTurn,maxPin);
            ge = new BowlingGameEntityInfo(rule);
            //ge.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ge;*/
        
        BowlingGameEntity bowlingGameEntity = null;
        BowlingRule rule=null;
        try {
            Statement stmt = connection.createStatement();
 
            ResultSet rs = stmt.executeQuery("select * from bowlingGame where id="+id);
            if(rs.next()) {
            	rule=new BowlingRuleInfo(id,rs.getInt("maxTurn"),rs.getInt("maxPin"));
                bowlingGameEntity=new BowlingGameEntityInfo(rule);
              
               return bowlingGameEntity;
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
    	BowlingRule rule=null;
    	BowlingGame bowlingGame=null;
    	if(entity!=null) {
    		rule = new BowlingRuleInfo(entity.getId(),entity.getMaxTurn(),entity.getMaxPin());
            bowlingGame = new BowlingGameInfo(rule);
            return bowlingGame;
    	}else return null;
    	
    }

    @Override
    public boolean remove(Integer key) {
        int flag = 0;
        try {
            Statement stmt = connection.createStatement();
            flag = stmt.executeUpdate("delete from bowlingGame where id="+key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag>0?true:false;
    	//return false;
    }

	@Override
	public void save(BowlingGame domain) throws SQLException {
		// TODO Auto-generated method stub
		doSave(domain.getEntity());
		
	}


}
