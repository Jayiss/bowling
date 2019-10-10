package training.adv.bowling.impl.zhuyurui;

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
        this.connection=connection;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> turnKeys=new ArrayList<>();
        String sql="SELECT id FROM test.turns where gameId="+foreignId+";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery(sql);

            while (resultSet.next()){
                int x=resultSet.getInt(1);
                TurnKey turnKey=new TurnKeyImpl(x,foreignId);
                turnKeys.add(turnKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnKeys;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        String sql="INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES ("+entity.getId().getId()+","+entity.getId().getForeignId()+","+ entity.getFirstPin()+","+entity.getSecondPin()+");";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        BowlingTurnEntity bowlingTurnEntity=new BowlingTurnEntityImpl();


        String sql="Select * from turns where gameId="+id.getForeignId()+"&id="+id.getId()+";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int i=resultSet.getInt(1);
                bowlingTurnEntity.setFirstPin(resultSet.getInt(3));
                bowlingTurnEntity.setSecondPin(resultSet.getInt(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bowlingTurnEntity;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        BowlingTurn bowlingTurn=new BowlingTurnImpl(entity.getFirstPin(),entity.getSecondPin());
        return bowlingTurn;
    }

    @Override
    public boolean remove(TurnKey key) {
        int i=0;
        String sql="delete FROM turns where id="+key.getId()+"&gameId="+key.getForeignId()+";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            i=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(i==0){
            return false;
        }else {
            return true;
        }

    }
}
