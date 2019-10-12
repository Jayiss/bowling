package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    private Connection connection=null;
    static int turnsId=0;
    public BowlingTurnDaoImpl(Connection connection){
        this.connection=connection;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        try{
            String sql="insert into turn(turn_id,first_pin,second_pin,game_id) values(?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,entity.getId().getId());
            preparedStatement.setInt(2,entity.getFirstPin());
            preparedStatement.setInt(3,entity.getSecondPin());
            preparedStatement.setInt(4,entity.getId().getForeignId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
        bowlingTurnEntity.setId(id);
        try (Connection connection =DBUtil.getConnection()){
            PreparedStatement preparedStatement =connection.prepareStatement("select * from turn " +
                    "where turn_id=? and game_id=?");
            preparedStatement.setInt(1,id.getId());
            preparedStatement.setInt(2,id.getForeignId());
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                bowlingTurnEntity.setFirstPin(resultSet.getInt(2));
                bowlingTurnEntity.setSecondPin(resultSet.getInt(3));
                return bowlingTurnEntity;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bowlingTurnEntity;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    public boolean remove(TurnKey key) {
        try{
            PreparedStatement preparedStatement =connection.prepareStatement("delete from " +
                    "turn  where turn_id=? and game_id=?");
            preparedStatement.setInt(1,key.getId());
            preparedStatement.setInt(2,key.getForeignId());
            int i=preparedStatement.executeUpdate();
            if (i>1){
            return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey>turnKeys=new ArrayList<>();
        try(Connection connection = DBUtil.getConnection()){
            PreparedStatement preparedStatement =connection.prepareStatement("select * from turn where game_id=?");
            preparedStatement.setInt(1,foreignId);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (!resultSet.next()){
                return null;
            }
            for (int i = 0; resultSet.next(); i++) {
                turnKeys.add(new TurnKeyImpl(resultSet.getInt(1),foreignId));
            }
            return turnKeys;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnKeys;
    }
}
