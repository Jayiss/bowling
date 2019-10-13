package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Connection connection;
    public BowlingTurnDaoImpl(Connection connection){this.connection=connection;}
    @Override
    public BowlingTurn load(TurnKey id) {
        return super.load(id);
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> turnKeyList=new ArrayList<>();
        String sql="select id from turn where foreignId = ?";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,foreignId);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                TurnKey turnKey=new TurnKeyImpl(rs.getInt(1),foreignId);
                turnKeyList.add(turnKey);
            }

        }catch (Exception e){}
        return turnKeyList;
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        return null;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        String sql="insert into turn values (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,entity.getId().getId());
            ps.setInt(2,entity.getId().getForeignId());
            ps.setInt(3,entity.getFirstPin());
            ps.setInt(4,entity.getSecondPin());
            ps.execute();
        }catch (Exception e){

        }
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    public boolean remove(TurnKey key) {
        String sql="delete turn where id = ? and foreignid = ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,key.getId());
            preparedStatement.setInt(2,key.getForeignId());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){}
        return false;
    }
}
