package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.impl.caokeke.BowlingTrunDaoImpl;
import training.adv.bowling.impl.caokeke.BowlingTurnImpl;

import java.sql.*;

public class DBUtil {
	
	private static final String url = "jdbc:h2:~/test";
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
//		Connection conn=getConnection();
//		Statement st = null;
//		try{
//			st = conn.createStatement();
//			String sql = "select id,name from persons";
//			ResultSet rs=st.executeQuery(sql);
//			while(rs.next()){
//				int id=rs.getInt(1);
//				String name=rs.getString(2);
//				System.out.println(id+name);
//			}
//
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
		BowlingTurnDao btd=new BowlingTrunDaoImpl();
		BowlingTurn bt=new BowlingTurnImpl(1,1,1,1);
		btd.save(bt);
	}
}
