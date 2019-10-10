package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BowlingGameDaoImpl implements BowlingGameDao {

    @Override
    public void save(BowlingGame domain) {
        Connection conn= DBUtil.getConnection();
        Statement st = null;
        try{
            st = conn.createStatement();
            String sql = "INSERT INTO games (id,curTurn) VALUES ("
                    +domain.getEntity().getId()+","+domain.getTurns().length+ ")";
            int rs=st.executeUpdate(sql);
            if(rs==0)
                System.out.println("insert error");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public BowlingGame load(Integer id) {
        BowlingGame bg;
        BowlingTurn []turns=new BowlingTurnImpl[new BowlingRuleImpl().getMaxTurn()+3];
        Connection conn= DBUtil.getConnection();
        Statement st = null;
        try{
            st = conn.createStatement();
            String sql1 = "SELECT * FROM turns WHERE foreignId="+id+" ORDER BY id";
            ResultSet rs1=st.executeQuery(sql1);
            for(int i=1;i<=turns.length && rs1.next();i++){
                turns[i]=new BowlingTurnImpl(
                        rs1.getInt("firstPin"),
                        rs1.getInt("secondPin"),
                        rs1.getInt("id"),
                        rs1.getInt("foreignId"));
            }
            String sql2 = "SELECT * FROM games WHERE id="+id+" ORDER BY id";
            ResultSet rs2=st.executeQuery(sql2);
            for(int i=1;i<=turns.length && rs1.next();i++){
                turns[i]=new BowlingTurnImpl(
                        rs1.getInt("firstPin"),
                        rs1.getInt("secondPin"),
                        rs1.getInt("id"),
                        rs1.getInt("foreignId"));
            }
            bg=new BowlingGameImpl();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Integer id) {

    }
}
