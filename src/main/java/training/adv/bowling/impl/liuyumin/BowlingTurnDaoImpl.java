package training.adv.bowling.impl.liuyumin;

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

    private Connection connection;
    private BowlingRuleImpl rule = new BowlingRuleImpl();
    // static int turnId = 0;
    public BowlingTurnDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        try {
            String sql = "insert into turn (turn_id,first_pin,second_pin,game_id) values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entity.getId().getId());
            preparedStatement.setInt(2, entity.getFirstPin());
            preparedStatement.setInt(3, entity.getSecondPin());
            preparedStatement.setInt(4, entity.getId().getForeignId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        try(Connection connection = DBUtil.getConnection()){
            String sql = "select * from turn where turn_id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, id.getId());
            ResultSet set= preparedStatement.executeQuery();
            BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();
            if (set.next()){
                bowlingTurnEntity.setId(new TurnKeyImpl(set.getInt(1), set.getInt(4)));
                bowlingTurnEntity.setFirstPin(set.getInt(2));
                bowlingTurnEntity.setSecondPin(set.getInt(3));
                return bowlingTurnEntity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    public boolean remove(TurnKey key) {
        try {
            String sql = "delete from  turn where turn_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, key.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> turnKeys = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection()){
            String sql = "select * from turn where game_id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, foreignId);
            ResultSet set= preparedStatement.executeQuery();
            while (set.next()){
                TurnKey turnKey = new TurnKeyImpl(set.getInt(1), set.getInt(4));
                turnKeys.add(turnKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnKeys;
    }
}