package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.impl.caokeke.BowlingTrunDaoImpl;
import training.adv.bowling.impl.caokeke.BowlingTurnImpl;

import java.sql.*;

public class DBUtil {
	
	private static final String url = "jdbc:h2:mem:~/test";
	private static final String user = "sa";
	private static final String pwd = "";
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(url, user, pwd);
			connection.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	public static void main(String [] args){
		Connection connection=DBUtil.getConnection();
		Statement st = null;
		try{
			st = connection.createStatement();
			String sql = "DELETE FROM games";
			int rs=st.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
