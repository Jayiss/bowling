package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTest {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBUtil.getConnection();
        connection.createStatement().execute("create table game(id int)");
    }
}
