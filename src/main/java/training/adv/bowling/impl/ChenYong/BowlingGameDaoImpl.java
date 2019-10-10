package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BowlingGameDaoImpl implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection conn)
    {
         connection=conn;
    }
    @Override
    public void save(BowlingGame domain) {
        PreparedStatement ptmt=null;
        try {
            String sql="insert into game (id,score,turn,pins) values (10,20,30,40)";
            ptmt= this.connection.prepareStatement(sql);
            ptmt.execute();
        }
        catch (SQLException e){}
    }

    @Override
    public BowlingGame load(Integer id) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }
}
