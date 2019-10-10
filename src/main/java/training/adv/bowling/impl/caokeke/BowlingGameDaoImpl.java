package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.impl.DBUtil;

import java.sql.*;

public class BowlingGameDaoImpl implements BowlingGameDao {

    private Connection connection;
    public BowlingGameDaoImpl(Connection connection){this.connection=connection;}
    @Override
    public void save(BowlingGame domain) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("INSERT INTO games (id,curTurn) VALUES (?,?)");
            st.setInt(1,domain.getEntity().getId());
            st.setInt(2,domain.getTurns().length);
            int rs=st.executeUpdate();
            if(rs==0)
                System.out.println("insert error");
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                st.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public BowlingGame load(Integer id) {
        BowlingGame bg=null;
        BowlingTurn []turns=new BowlingTurnImpl[new BowlingRuleImpl().getMaxTurn()+3];
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("SELECT * FROM turns WHERE foreignId=? ORDER BY id");
            st.setInt(1,id);
            ResultSet rs1=st.executeQuery();

            for(int i=1;i<=turns.length && rs1.next();i++){
                turns[i]=new BowlingTurnImpl(
                        rs1.getInt("firstPin"),
                        rs1.getInt("secondPin"),
                        rs1.getInt("id"),
                        rs1.getInt("foreignId"));
            }
            st = connection.prepareStatement("SELECT * FROM games WHERE id=?");
            st.setInt(1,id);
            ResultSet rs2=st.executeQuery();

            int curTurn=0;
            if(rs2.next())
                curTurn=rs2.getInt("curTurn");
            else return bg;
            BowlingGameEntity bge=new BowlingGameEntityImpl(id);
            bg=new BowlingGameImpl(curTurn,turns,bge);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                st.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return bg;
    }

    @Override
    public boolean remove(Integer id) {
        PreparedStatement st = null;
        try{
            st = connection.prepareStatement("DELETE FROM games WHERE id=?");
            st.setInt(1,id);
            int rs=st.executeUpdate();
            if(rs==0)
                return false;
            st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                st.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
