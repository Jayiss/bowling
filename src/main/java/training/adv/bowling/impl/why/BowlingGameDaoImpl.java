package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BowlingGameDaoImpl extends AbstractDao<GameEntity,BowlingGame,Integer> implements BowlingGameDao {

    private Connection con;
    public BowlingGameDaoImpl(Connection con){
        this.con=con;
    }

    @Override
    protected void doSave(GameEntity entity) {
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
    protected GameEntity doLoad(Integer id) {
        String sql="select * from game where id=?";
        GameEntity entity=new BowlingGameEntityImpl();
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet set=statement.executeQuery();
            set.next();
            entity.setId(set.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    protected BowlingGame doBuildDomain(GameEntity entity) {

        return null;
    }

    @Override
    public boolean remove(Integer key) {
        return false;
    }
}
