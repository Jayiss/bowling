package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTrunDaoImpl implements BowlingTurnDao {

    private Connection connection;

    public BowlingTrunDaoImpl(Connection connection){this.connection=connection;}

    @Override
    public void save(BowlingTurn domain) {
        BowlingTurnEntity bte=domain.getEntity();
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("INSERT INTO turns (id,foreignId,firstPin,secondPin) VALUES(?,?,?,?)");
            st.setInt(1,bte.getId().getId());
            st.setInt(2,bte.getId().getForeignId());
            st.setInt(3,bte.getFirstPin());
            st.setInt(4,bte.getSecondPin());
            int rs=st.executeUpdate();
            if(rs==0)
                System.out.println("insert error");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<BowlingTurnEntity> batchLoad(int foreignId) {
        List<BowlingTurnEntity> res=new ArrayList<BowlingTurnEntity>();
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("SELECT * FROM turns WHERE foreignId=?");
            st.setInt(1,foreignId);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                res.add(new BowlingTurnEntityImpl(
                        rs.getInt("firstPin"),
                        rs.getInt("secondPin"),
                        rs.getInt("id"),
                        rs.getInt("foreignId")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void batchRemove(int foreignId) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("DELETE FROM turns WHERE foreignId=?");
            st.setInt(1,foreignId);
            int rs=st.executeUpdate();
            if(rs==0)
                System.out.println("insert error");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
