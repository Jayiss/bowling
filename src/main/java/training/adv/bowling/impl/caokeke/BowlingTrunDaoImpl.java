package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BowlingTrunDaoImpl implements BowlingTurnDao {
    @Override
    public void save(BowlingTurn domain) {
        BowlingTurnEntity bte=domain.getEntity();
        Connection conn= DBUtil.getConnection();
        Statement st = null;
        try{
            st = conn.createStatement();
            String sql = "INSERT INTO turns (id,foreignId,firstPin,secondPin) VALUES ("
                    +bte.getId().getId()+","
                    +bte.getId().getForeignId()+","
                    +bte.getFirstPin()+","
                    +bte.getSecondPin()+")";
            int rs=st.executeUpdate(sql);
            if(rs==0)
                System.out.println("insert error");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<BowlingTurnEntity> batchLoad(Integer id) {
        List<BowlingTurnEntity> res=new ArrayList<BowlingTurnEntity>();
        Connection conn= DBUtil.getConnection();
        Statement st = null;
        try{
            st = conn.createStatement();
            String sql = "SELECT * FROM turns WHERE foreignId="+id;
            ResultSet rs=st.executeQuery(sql);
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
    public void batchRemove(Integer id) {
        Connection conn= DBUtil.getConnection();
        Statement st = null;
        try{
            st = conn.createStatement();
            String sql = "DELETE FROM turns WHERE foreignId="+id;
            int rs=st.executeUpdate(sql);
            if(rs==0)
                System.out.println("insert error");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
