package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.impl.DBUtil;

import java.sql.*;

public class TableCreate {

    private  Connection connection = DBUtil.getConnection();
    public int createBowlingGame() throws SQLException {


        Statement statement = this.connection.createStatement();
        String str = "SELECT * FROM BOWLINGGAME";
        ResultSet re = statement.executeQuery(str);
        if(re.next())
            return 1;
        else{
            str = "CREATE TABLE BOWLINGGAME(ID int )";
            int i = statement.executeUpdate(str);
            return 1;
        }
    }

    public  int createBowlingTurn() throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT * FROM BOWLINGTURN";
        ResultSet re = statement.executeQuery(str);
        if(re.next())
            return 1;
        else{
            str = "CREATE TABLE BOWLINGTURN(ID int,BOWLINGGAMEID int,FIRSTPIN int ,SECONDPIN int )";
            int i = statement.executeUpdate(str);
            return 1;
        }
    }
}
