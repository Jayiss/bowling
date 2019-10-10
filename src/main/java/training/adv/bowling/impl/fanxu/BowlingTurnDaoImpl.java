package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Connection connection;
    public BowlingTurnDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> turnKeyList = new ArrayList<>();
        try{
            String sql = "select id from turn where foreign_id= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,foreignId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                turnKeyList.add(new BowlingTurnKeyInfo(id,foreignId));
            }
        }catch (SQLException sqlException){

        }
        return turnKeyList;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        try {
            String insertSql = "insert into turn values(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);
            statement.setInt(1,entity.getId().getId());
            statement.setInt(2,entity.getId().getForeignId());
            statement.setInt(3,entity.getFirstPin());
            statement.setInt(4,entity.getSecondPin());
            statement.executeUpdate();
        }catch (SQLException sqlException){

        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        BowlingTurnEntity bowlingTurnEntity = new BowlingTurnInfo();
        String querySql = "select * from turn where  id = ? && foreign_id= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(querySql);
            statement.setInt(1,id.getId());
            statement.setInt(2,id.getForeignId());
            ResultSet rs = statement.executeQuery();
            bowlingTurnEntity.setFirstPin(rs.getInt(3));
            bowlingTurnEntity.setSecondPin(rs.getInt(4));
            bowlingTurnEntity.setId(id);
            return bowlingTurnEntity;
        } catch (SQLException e) {
            e.printStackTrace();
              bowlingTurnEntity = new BowlingTurnInfo();
              return bowlingTurnEntity;
        }

    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        BowlingTurn bowlingTurn = new BowlingTurnImpl(entity.getFirstPin(),entity.getSecondPin());
        return bowlingTurn;
    }

    @Override
    public boolean remove(TurnKey key) {
        String delStatement = "delete from turn where   id = ? && foreign_id = ?";
        boolean isRemoved = false;
        try {
            PreparedStatement statement = connection.prepareStatement(delStatement);
            statement.setInt(1,key.getId());
            statement.setInt(2,key.getForeignId());
            isRemoved = statement.execute();
            return isRemoved;
        } catch (SQLException e) {
            e.printStackTrace();
            return isRemoved;
        }
    }
}
