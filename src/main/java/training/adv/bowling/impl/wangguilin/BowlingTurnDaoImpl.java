package training.adv.bowling.impl.wangguilin;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.wangguilin.TurnKeyImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao {

    private Connection connection = null;

    public BowlingTurnDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> l = new ArrayList<TurnKey>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select id from turn where foreignId="+foreignId);
            while(rs.next()){
                Integer id = rs.getInt("id");
                TurnKey key = new TurnKeyImpl(id,foreignId);
                l.add(key);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return l;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        int first = entity.getFirstPin();
        TurnKey key = entity.getId();
        int foreignId = key.getForeignId();
        int id = key.getId();
        try {
            Statement stmt = connection.createStatement();
            if(entity.getSecondPin()==null){
                Integer second = null;
                stmt.executeUpdate("insert into turn values("+id+","+foreignId+","+first+","+second+")");
            }else{
                Integer second = entity.getSecondPin();
                stmt.executeUpdate("insert into turn values("+id+","+foreignId+","+first+","+second+")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        Integer foreignId = id.getForeignId();
        Integer Id =id.getId();
        BowlingTurnEntity bte = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from turn where foreignId="+foreignId+" and id="+Id);
            if(rs.next()) {
                bte=new BowlingTurnEntityImpl();
                int firstPin = rs.getInt("firstPin");
                Integer secondPin = null;
                if (!(rs.getString("secondPin") == null))
                    secondPin = rs.getInt("secondPin");
                TurnKey key = new TurnKeyImpl(Id, foreignId);
                bte.setFirstPin(firstPin);
                bte.setSecondPin(secondPin);
                bte.setId(key);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bte;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        Integer first = entity.getFirstPin();
        Integer second = entity.getSecondPin();
        BowlingTurn bowlingTurn = new BowlingTurnImpl(first,second);
        return bowlingTurn;
    }

    @Override
    public boolean remove(TurnKey key) {
        int foreignId = key.getForeignId();
        int id = key.getId();
        int flag = 0;
        try {
            Statement stmt = connection.createStatement();
            flag = stmt.executeUpdate("delete from turn where foreignId="+foreignId+" and id="+id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return flag>0?true:false;
    }
}


