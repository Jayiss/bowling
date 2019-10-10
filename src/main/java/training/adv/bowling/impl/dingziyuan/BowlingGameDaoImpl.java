package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.io.Serializable;
import java.sql.*;
import java.util.UUID;

public class BowlingGameDaoImpl extends AbstractDao<GameEntity,BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

//    @Override
//    public boolean remove(Integer id) {
//        return false;
//    }

    @Override
    protected void doSave(GameEntity entity) {
//        try {
//            String insertSql =
//                    "insert into GAMES(TOTAL_SCORE) values(?)";
//            PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
//            stmt.setString(1, domain.getTotalScore().toString());
//            stmt.executeQuery();
//
//            ResultSet resultSet = stmt.getGeneratedKeys();
//            Serializable newKey = null;
//            if (resultSet.next()) {
//                newKey = (Serializable) resultSet.getObject(1);
//            }
//            System.out.println(domain.getTotalScore());
//            System.out.println(newKey);
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected GameEntity doLoad(Integer id) {
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(GameEntity entity) {
        return null;
    }

    @Override
    public boolean remove(Integer key) {
        return false;
    }
}
