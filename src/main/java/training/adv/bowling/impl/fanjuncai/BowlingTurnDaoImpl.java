package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {
    @Override
    protected List<TurnKey> loadAllKey(int foreignId){

        ArrayList<TurnKey> TurnKeys = new ArrayList<>();

        Connection connection = DBUtil.getConnection();
        String str = "SELECT * FROM BOWLINGTURN WHERE BOWLINGGAMEID = ?";
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(str);
            pstm.setInt(1,foreignId);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){

                TurnKeyImpl turnKeyImpl = new TurnKeyImpl();
                turnKeyImpl.setId(rs.getInt("ID"));
                turnKeyImpl.setId(rs.getInt("BOWLINGGAMEID"));
                TurnKeys.add(turnKeyImpl);
            }

            connection.commit();
            pstm.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return TurnKeys;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity){
        if(entity!=null){
            Connection connection = DBUtil.getConnection();
            String str = "SELECT * FROM BOWLINGGAME WHERE ID = ?";
            PreparedStatement pstm = null;
            try {
                pstm = connection.prepareStatement(str);
                pstm.setInt(1,entity.getId().getForeignId());

                ResultSet rs = pstm.executeQuery();

                if(!rs.next()){
                    str = "INSERT INTO BOWLINGGAME VALUES(?)";
                    pstm = connection.prepareStatement(str);
                    pstm.setInt(1,entity.getId().getForeignId());
                    pstm.executeUpdate();
                }

                String sql = "INSERT INTO BOWLINGTURN VALUES(?,?,?,?)";
                pstm = connection.prepareStatement(sql);
                pstm.setInt(1,entity.getId().getId());
                pstm.setInt(2,entity.getId().getForeignId());
                pstm.setInt(3,entity.getFirstPin());
                pstm.setInt(4,entity.getSecondPin());
                pstm.executeUpdate();

                connection.commit();
                pstm.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public BowlingTurnEntity doLoad(TurnKey id){
        if(id !=null){
            BowlingTurnEntityImpl bowlingTurnEntity = new BowlingTurnEntityImpl();
            Connection connection = DBUtil.getConnection();
            String str = "SELECT * FROM BOWLINGTURN WHERE ID = ? AND BOWLINGGAMEID = ?";
            PreparedStatement pstm = null;
            try {
                pstm = connection.prepareStatement(str);
                pstm.setInt(1,id.getId());
                pstm.setInt(2,id.getForeignId());
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    bowlingTurnEntity.setId(id);
                    bowlingTurnEntity.setFirstPin(rs.getInt("FIRSTPIN"));
                    bowlingTurnEntity.setSecondPin(rs.getInt("SECONDPIN"));
                }
                connection.commit();
                pstm.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return bowlingTurnEntity;
        }
        else
            return null;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        if(entity !=null){
            BowlingTurnImpl bowlingTurn = new BowlingTurnImpl();
            if(entity.getSecondPin() == null){
                bowlingTurn.setNumOfPins(1);
                bowlingTurn.setFirstPin(entity.getFirstPin());
            }

            else{
                bowlingTurn.setNumOfPins(2);
                bowlingTurn.setFirstPin(entity.getFirstPin());
                bowlingTurn.setSecondPin(entity.getSecondPin());
            }
            return bowlingTurn;
        }
        else
            return null;
    }

    @Override
    public boolean remove(TurnKey key){
        if(key !=null){
            Connection connection = DBUtil.getConnection();
            String str = "SELECT * FROM BOWLINGTURN WHERE ID = ? AND BOWLINGGAMEID = ?";
            PreparedStatement pstm = null;
            try {
                pstm = connection.prepareStatement(str);
                pstm.setInt(1,key.getId());
                pstm.setInt(2,key.getForeignId());
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    String sql = "DELETE FROM BOWLINGTURN WHERE ID = ? AND BOWLINGGAMEID = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    pstm.setInt(1,key.getId());
                    pstm.setInt(2,key.getForeignId());
                    preparedStatement.execute();
                    preparedStatement.close();
                    connection.commit();
                    pstm.close();
                    connection.close();

                }
                else{
                    connection.commit();
                    pstm.close();
                    connection.close();
                    return false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        else
            return false;
    }
}
