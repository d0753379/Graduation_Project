package ProjectPackage;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class SQL {

	static ResultSet select(String sql) {
		//看db裡有沒有資料 如果沒有的話要用insert 有的話用update
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/graduation_project?useUnicode=true&characterEncoding=UTF-8 & serverTimezone=UTC & user=hj&password=test1234&useSSL=true");
		    Statement stmt = conn.createStatement();
		    System.out.println("select->資料庫連線成功");
		    
		    ResultSet rs = stmt.executeQuery(sql);
		    if(rs.next()) {
		    	return rs;
		    }
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
		      e.printStackTrace();
	    }
		return null;
	}
	static int insert_update(String sql) {
		//看db裡有沒有資料 如果沒有的話要用insert 有的話用update
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/graduation_project?useUnicode=true&characterEncoding=UTF-8 & serverTimezone=UTC & user=hj&password=test1234&useSSL=true");
		    Statement stmt = conn.createStatement();
		    System.out.println("insert_Update->資料庫連線成功");
		    
		    stmt.executeUpdate(sql);
		    return 0;
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
		      e.printStackTrace();
	    }
		return 1;
	}
	public static void main(String[] args) throws IOException, SQLException {
		/*String sql = "SELECT * FROM `user` WHERE 1";
		ResultSet rs = select(sql);
		while(true) {
			System.out.println(rs.getString("User_ID"));
			if(!rs.next())break;
		}
		*/
		String UserID = "test2";
		String Game_money = "200";
		String Virtual_money = "300";
		String Metal = "300";
		String Wood = "300";
		String Stone = "300";
		String Food = "300";
		String Tool = "300";
		String sql;
		sql = "UPDATE `asset` SET `Game_money`='"+Game_money+"',`Virtual_money`='"+Virtual_money+"',`Metal`='"+Metal+"',`Wood`='"+Wood+"',`Stone`='"+Stone+"',`Food`='"+Food+"',`Tool`='"+Tool+"' WHERE `User_ID`= '"+UserID+"'";
		SQL.insert_update(sql);
	}
}
