package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<GameEntity,BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    protected void doSave(GameEntity entity) {
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        TurnEntity[] turnEntities = entity.getTurnEntities();
//        for(TurnEntity turnEntity:turnEntities){
//            new BowlingTurnDaoImpl(connection).doBuildDomain((BowlingTurnEntity) turnEntity);
//        }
        String insertSql = "insert into bowling_game values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,0);//测试数据；
            preparedStatement.setInt(3,maxTurn);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected GameEntity doLoad(Integer id) {
        GameEntity gameEntity;
        String querySql = "select * from bowling_game where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(querySql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            int maxTunrn = resultSet.getInt(3);
            List<BowlingTurnEntity> turns = new BowlingTurnDaoImpl(connection).batchLoad(id);
            gameEntity = new BowlingGameInfo(id,maxTunrn);
            gameEntity.setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
            return  gameEntity;

        } catch (SQLException e) {
            e.printStackTrace();
            gameEntity = new BowlingGameInfo();
            return gameEntity;
        }


    }

    @Override
    protected BowlingGame doBuildDomain(GameEntity entity) {
        TurnEntity[] turnEntities = entity.getTurnEntities();
        BowlingTurn[] bowlingTurns = new BowlingTurnImpl[entity.getMaxTurn()+2];
        for(int i = 0 ;i<turnEntities.length;i++){
            BowlingTurn bowlingTurn = (BowlingTurn)turnEntities[i];
//            int firstScore = bowlingTurn.getFirstScore();
//            int secondScore = (BowlingTurnEntity)turnEntities[i];
//            bowlingTurns[i] = new BowlingTurnImpl(firstScore,secondScore);
        }
        BowlingGame bowlingGame = new BowlingGameImpl(new BowlingRuleImpl(),bowlingTurns);
        return bowlingGame;
    }

    @Override
    public boolean remove(Integer key) {
        String delStatement = "delete from turn where id = ? ";
        boolean isRemoved = false;
        try {
            PreparedStatement statement = connection.prepareStatement(delStatement);
            statement.setInt(1,key);
            isRemoved = statement.execute();
            return isRemoved;
        } catch (SQLException e) {
            e.printStackTrace();
            return isRemoved;
        }
    }
}


