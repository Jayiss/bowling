package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }



    @Override
    protected void doSave(BowlingGameEntity entity) {
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        int maxPins = entity.getMaxPin();
        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();
        BowlingTurn[] bowlingTurns = new BowlingTurn[maxTurn+2];
        for(int i = 0;i<bowlingTurns.length;i++){
            bowlingTurns[i] = new BowlingTurnImpl(turnEntities[i].getFirstPin(),turnEntities[i].getSecondPin(),turnEntities[i].getId());
        }
        int score = 0;
        Integer[] scores = new BowlingRuleImpl().calcScores(bowlingTurns);
        for (int i = 0;i<scores.length;i++){
            score+=scores[i].intValue();
        }
        String insertSql = "insert into bowling_game values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,score);//测试数据；
            preparedStatement.setInt(3,maxTurn);
            preparedStatement.setInt(4,maxPins);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        BowlingGameEntity gameEntity;
        String querySql = "select * from bowling_game where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(querySql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int maxTurn = resultSet.getInt(3);
            int maxPins = resultSet.getInt(4);
            List<BowlingTurnEntity> turns = new BowlingTurnDaoImpl(connection).batchLoad(id);
            gameEntity = new BowlingGameInfo(id,maxTurn,maxPins);
            //give 0 test
            gameEntity.setTurnEntities(turns.toArray(new BowlingTurnEntity[maxTurn+2]));
            return  gameEntity;

        } catch (SQLException e) {
            e.printStackTrace();
            gameEntity = new BowlingGameInfo();
            return gameEntity;
        }

    }


    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingTurnEntity[] bowlingTurnEntitys = entity.getTurnEntities();
        BowlingTurn[] bowlingTurns = new BowlingTurnImpl[entity.getMaxTurn()+2];
        for(int i = 0 ;i<bowlingTurnEntitys.length;i++){
            BowlingTurnEntity bowlingTurnEntity = bowlingTurnEntitys[i];
            Integer firstScore = bowlingTurnEntity==null?null:bowlingTurnEntity.getFirstPin();
            Integer secondScore = bowlingTurnEntity==null?null:bowlingTurnEntity.getSecondPin();
            TurnKey id = new BowlingTurnKeyInfo(i+1,entity.getId());
            bowlingTurns[i] = new BowlingTurnImpl(firstScore,secondScore,id);
        }
        BowlingGame bowlingGame = new BowlingGameImpl(new BowlingRuleImpl(),bowlingTurns,entity);
        return bowlingGame;
    }

    @Override
    public boolean remove(Integer key) {
        String delStatement = "delete from bowling_game where id = ? ";
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


