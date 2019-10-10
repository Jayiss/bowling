package training.adv.bowling.impl.why;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl  extends AbstractBatchDao implements BowlingTurnDao {

    public static void main(String args[]){
        Connection connection=DBUtil.getConnection();
        BowlingTurnDaoImpl dao=new BowlingTurnDaoImpl(connection);
        BowlingTurnEntity entity=new BowlingTurnEntityImpl();
        entity.setId(new TurnKeyImpl(1,1));
        entity.setFirstPin(1);
        dao.doSave(entity);
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection con;
    BowlingTurnDaoImpl(Connection connection){
        this.con=connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        String sql="select id,gameId from turns where gameId=? ";
        List<TurnKey> keys=new ArrayList<>();
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,foreignId);
            ResultSet set=statement.executeQuery();
            while (set.next()){
                Integer id=set.getInt("id");
                Integer gameId=set.getInt("gameId");
                keys.add(new TurnKeyImpl(id,gameId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return keys;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        String sql="insert into turns values(?,?,?,?)";
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            TurnKey key=entity.getId();
            statement.setInt(1,key.getId());
            statement.setInt(2,key.getForeignId());
            statement.setInt(3,entity.getFirstPin());
            if (entity.getSecondPin()!=null)
            statement.setInt(4,key.getId());
            else  statement.setNull(4, Types.INTEGER);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        String sql="select * from turns where id=? and gameId=?";
        BowlingTurnEntity entity=new BowlingTurnEntityImpl();
        entity.setId(id);
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,id.getId());
            statement.setInt(2,id.getForeignId());
            ResultSet set=statement.executeQuery();
            set.next();
            entity.setFirstPin(set.getInt("first"));
            entity.setSecondPin(set.getInt("second"));
            if (set.wasNull())entity.setSecondPin(null);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return new BowlingTurnImpl(entity);
    }

    @Override
    public boolean remove(TurnKey key) {
        String sql="delete from turns where id=? and gameId=?";
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            statement.setInt(1,key.getId());
            statement.setInt(2,key.getForeignId());
            int i=statement.executeUpdate();
            if (i>0)return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
