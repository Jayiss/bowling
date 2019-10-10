package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.Game;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.AbstractGame;
import training.adv.bowling.impl.DBUtil;

import java.awt.font.GlyphMetrics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BowlingGameDaoImpl extends AbstractDao<GameEntity,BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection){
        this.connection=connection;
    }




    @Override
    protected void doSave(GameEntity entity) {
        String sql="INSERT INTO games (id) VALUES ("+entity.getId()+");";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected GameEntity doLoad(Integer id){
     GameEntity gameEntity=new BowlingGameEntityImpl();
     String sql="SELECT * FROM test.turns where id="+id+";";
     try {
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery();
         while (resultSet.next()){
             int i=resultSet.getInt(1);
             gameEntity.setId(i);
         }


     } catch (SQLException e) {
            e.printStackTrace();
     }
    return gameEntity;
    }


    @Override
    protected BowlingGame doBuildDomain(GameEntity entity) {
        BowlingGame bowlingGame=new BowlingGameImpl(BowlingRuleImpl.getInstance());
        return bowlingGame;

    }

    @Override
    public boolean remove(Integer key) {

        int i=0;
        String sql="delete FROM turns where id="+key+";";
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
