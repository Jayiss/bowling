package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {

    private Connection connection;
    public BowlingGameDaoImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    protected void doSave(BowlingGameEntity entity) {
        try{
            PreparedStatement statement = connection.prepareStatement("insert into GAME values (?,?,?)");
            statement.setInt(1,entity.getId());
            statement.setInt(2,entity.getMaxTurn());
            statement.setInt(3,entity.getTurnEntities().length);
            int cnt = statement.executeUpdate();
            connection.commit();
            //if(cnt > 0) connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        BowlingGameEntity entity = new BowlingGameImpl(new BowlingRuleImpl(),id);
        BowlingTurnEntity[] turnEntities;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from GAME where GAMEID=?");
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            int len = 0, i = 0;
            if(rs.next()) len = rs.getInt("TURN_NUM");
            PreparedStatement statement2 = connection.prepareStatement("select * from TURN where GAMEID=?");
            statement2.setInt(1,id);
            ResultSet rs2 = statement2.executeQuery();
            turnEntities = new BowlingTurnEntityImpl[len];
            while (rs2.next()){
                int firstPin = rs2.getInt("FIRSTPIN");
                turnEntities[i] = new BowlingTurnEntityImpl(new BowlingTurnImpl());
                turnEntities[i].setFirstPin(firstPin);
                turnEntities[i].setSecondPin(rs2.getInt("SECONDPIN"));
                turnEntities[i].setId(new TurnKeyImpl(rs2.getInt("TURNID"), rs2.getInt("GAMEID")));
                i++;
            }
            entity.setTurnEntities(turnEntities);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return new BowlingGameImpl(new BowlingRuleImpl(), entity.getId());
    }

    @Override
    public boolean remove(Integer key) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from GAME where GAMEID=?");
            statement.setInt(1,key);
            statement.executeUpdate();
            connection.commit();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
