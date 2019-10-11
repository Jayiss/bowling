package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity,BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity){
        Statement stmt = null;
        try {
           stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        int maxPin = entity.getMaxPin();
        try {
            stmt.executeUpdate("insert into game(id,maxTurn,maxPin) values("+id+","+maxTurn+","+maxPin+")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected BowlingGameEntity doLoad(Integer id){
        BowlingGameEntity bowlingGameEntity = null;
        //ArrayList<BowlingTurnEntity> turnEntities = new ArrayList<BowlingTurnEntity>();
        Integer maxTurn = null;
        Integer maxPin = null;
        //BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
        try {
            Statement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("select * from game,turn where game.id="+id+" and game.id=turn.id");
            ResultSet rs = stmt.executeQuery("select * from game where id="+id);
            if(rs.next()) {
                bowlingGameEntity=new BowlingGameEntityImpl(id,rs.getInt("maxTurn"),rs.getInt("maxPin"));
               /* Integer first = rs.getInt("firstPin");
                Integer turnId = rs.getInt("turn.id");
                TurnKey k = new TurnKeyImpl(id,turnId);
                turnEntities.add(new BowlingTurnEntityImpl(k,first,second));*/
               return bowlingGameEntity;
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //bowlingGameEntity.setTurnEntities(turnEntities.toArray(new BowlingTurnEntity[turnEntities.size()]));
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity){
        BowlingRule rule = new BowlingRuleImpl();
        BowlingGameImpl bowlingGame = new BowlingGameImpl(entity.getId(), rule);
        return bowlingGame;
    }

    @Override
    public boolean remove(Integer key) {
        int flag = 0;
        try {
            Statement stmt = connection.createStatement();
            flag = stmt.executeUpdate("delete from game where id="+key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag>0?true:false;
    }
}

