package ProjectPackage;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class SQL {

	static ResultSet select(String sql) {
		//看db裡有沒有資料 如果沒有的話要用insert 有的話用update
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/graduation_project?useUnicode=true&characterEncoding=UTF-8 & serverTimezone=UTC & user=hj&password=test1234&useSSL=true");
		    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
		    conn.isClosed();
		    conn.close();
		    stmt.close();
		    
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
		String User_ID = "abc123";
		String User_password = "abc123";
		String Game_money = "200";
		String Virtual_money = "300";
		String Metal = "300";
		String Wood = "300";
		String Stone = "300";
		String Food = "300";
		String Tool = "300";
		String Time = "1";
		String sql;
		int X = 1;
		int Y = 1;
		String Task_name = "點擊收成";
		String Period_name = "貝殼";
		String PassLevel = "2";
		//String Build_time = "2000";
		//String Build_name = "StoneBuilding";
		//sql = "UPDATE `asset` SET `Game_money`='"+Game_money+"',`Virtual_money`='"+Virtual_money+"',`Metal`='"+Metal+"',`Wood`='"+Wood+"',`Stone`='"+Stone+"',`Food`='"+Food+"',`Tool`='"+Tool+"' WHERE `User_ID`= '"+User_ID+"'";
		//sql = "INSERT INTO `land`(`User_ID`, `Build_name`, `X`, `Y`) VALUES ('"+User_ID+"','"+Build_name+"','"+X+"','"+Y+"')";
		//sql = "INSERT INTO `land`(`User_ID`, `Build_name`, `X`, `Y`, `Build_time`) VALUES ('"+User_ID+"','"+Build_name+"','"+X+"','"+Y+"','"+Build_time+"')";
		//sql = "UPDATE `land` SET `Build_production`='3' WHERE `User_ID`='"+User_ID+"' && `X`='"+X+"' && `Y`='"+Y+"'";
		//sql = "UPDATE `schedule` SET `Task_name`='"+Task_name+"',`Period_name`='"+Period_name+"',`Pass`='"+Pass+"' WHERE User_ID = '"+User_ID+"'";			
		//sql = "UPDATE `schedule` SET `Task_name`='"+Task_name+"',`Period_name`='"+Period_name+"',`PassLevel`='"+PassLevel+"' WHERE User_ID = '"+User_ID+"'";			
		//sql = "SELECT * FROM `land` WHERE User_ID = '"+User_ID+"' ORDER BY 'Y' ASC,'X' ASC";
		sql = "INSERT INTO `user`(`User_ID`, `User_password`) VALUES ('"+User_ID+"','"+User_password+"')";

		SQL.insert_update(sql);
		/*
		JSONObject jsonout = new JSONObject();
		ResultSet rs = SQL.select(sql);
		
		int RScount = 0;
		//RS的指標指到最後一個Row 數有幾個
		if(rs.last()) {
			RScount = rs.getRow();
		}
		
		rs.first();
		System.out.println(RScount);
		
		
		do{
			if(rs!=null){
				JSONObject JSONTemp = new JSONObject();
	        	String building_name = rs.getString("Build_name");
	        	int building_time = rs.getInt("Build_time");
	        	X = rs.getInt("X");
	        	Y = rs.getInt("Y");
	        	int production = rs.getInt("Build_production");
	        	
	        	JSONTemp.put("building_name",building_name);
	        	JSONTemp.put("building_time",building_time);
	        	JSONTemp.put("production",production);
	        	jsonout.put(X+"-"+Y,JSONTemp);
			}
		}while(rs.next());
		jsonout.put("Data_name","Load_build");
		String str = jsonout.toString();
	    System.out.println(str);
	    //System.out.println(jsonout.getJSONObject("1-1"));
	     
		//sql = "SELECT * FROM `land` WHERE User_ID = '"+User_ID+"'";
        //ResultSet rs = SQL.select(sql);
        
        
		/*
		if(rs==null) {
			System.out.println("Error");
		}
		System.out.println(rs.getString("Build_name").toString());
		*/
		
	}
}
