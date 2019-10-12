package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity,BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection=null;

    public BowlingGameDaoImpl(Connection connection){
        this.connection=connection;
    }
    @Override
    public void save(BowlingGame domain) {
        doSave(domain.getEntity());
    }

    @Override
    public BowlingGame load(Integer id) {

        return (BowlingGame) doLoad(id);
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        try(Connection connection= DBUtil.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement("select  *from game where game_id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (!resultSet.next()){
                return null;
            }
            BowlingGameImpl bowlingGameEntity=new BowlingGameImpl(new BowlingRuleImpl());
            bowlingGameEntity.setId(resultSet.getInt(1));
            bowlingGameEntity.setTotalScore(resultSet.getInt(2));
            return bowlingGameEntity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        try{
            PreparedStatement preparedStatement =connection.prepareStatement("delete from game  where game_id=?");
            preparedStatement.setInt(1,id);
            int i=preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    protected void doSave(BowlingGameImpl entity) {
        try{
            String sql="insert into  game (game_id,total_score,max_turn,max_pin) values(?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.setInt(2,entity.getTotalScore());
            preparedStatement.setInt(3,entity.getMaxTurn());
            preparedStatement.setInt(4,entity.getMaxPin());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        try{
            entity=(BowlingGameImpl)entity;
            String sql="insert into  game (game_id,total_score,max_turn,max_pin) values(?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.setInt(2,((BowlingGameImpl)entity).getTotalScore());
            preparedStatement.setInt(3,entity.getMaxTurn());
            preparedStatement.setInt(4,entity.getMaxPin());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
