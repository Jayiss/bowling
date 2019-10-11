package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Connection connection;
    public BowlingTurnDaoImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> keys = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            statement = connection.prepareStatement("select TURNID from TURN where GAMEID=?");
            statement.setInt(1,foreignId);
            rs = statement.executeQuery();
            connection.commit();
            while (rs.next()){
                keys.add(new TurnKeyImpl(rs.getInt("TURNID"), foreignId));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                assert rs != null;
                rs.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return keys;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        try{
            PreparedStatement statement = connection.prepareStatement("insert into TURN values (?,?,?,?)");
            statement.setInt(1,entity.getId().getId());
            statement.setInt(2,entity.getId().getForeignId());
            statement.setInt(3,entity.getFirstPin());
            statement.setInt(4,entity.getSecondPin());
            int cnt = statement.executeUpdate();
            connection.commit();
            //if(cnt > 0) connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl(new BowlingTurnImpl());
        try {
            statement = connection.prepareStatement("select * from TURN where TURNID=? and GAMEID=?");
            statement.setInt(1,id.getId());
            statement.setInt(2,id.getForeignId());
            rs = statement.executeQuery();
            connection.commit();
            if(rs.next()){
                turnEntity.setId(id);
                turnEntity.setFirstPin(rs.getInt("FIRSTPIN"));
                turnEntity.setSecondPin((rs.getInt("SECONDPIN")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                assert rs != null;
                rs.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return turnEntity;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return new BowlingTurnImpl(new int[]{entity.getFirstPin(),entity.getSecondPin()},entity.getId());
    }

    @Override
    public boolean remove(TurnKey key) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from TURN where TURNID=? and GAMEID=?");
            statement.setInt(1,key.getId());
            statement.setInt(2,key.getForeignId());
            statement.executeUpdate();
            connection.commit();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
