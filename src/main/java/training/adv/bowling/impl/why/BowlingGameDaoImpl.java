package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity,BowlingGame,Integer> implements BowlingGameDao {

    private Connection con;
    public BowlingGameDaoImpl(Connection con){
        this.con=con;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        String sql="insert into game values(?)";
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        String sql="select * from game where id=?";
        BowlingGameEntity entity=new BowlingGameEntityImpl();
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet set=statement.executeQuery();
            if (set.next())
            entity.setId(set.getInt("id"));
            else return null;
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return  new BowlingGameImpl(BowlingRuleImpl.getInstance(),entity);
    }

    @Override
    public boolean remove(Integer key) {
        String sql="delete from game where id=?";
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,key);
            int i =statement.executeUpdate();
            if (i>0)return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
