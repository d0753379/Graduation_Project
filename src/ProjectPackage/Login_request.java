package ProjectPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class Login_request {
	static void Login(Socket server,JSONObject jsonin) throws IOException {		
        JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        String str = new String(); 
        byte b[] = new byte[1024];
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/graduation_project?useUnicode=true&characterEncoding=UTF-8 & serverTimezone=UTC & user=hj&password=test1234&useSSL=true");
			System.out.println("資料庫連線成功");
			String User_ID=jsonin.getString("User_ID"); //從client端收取的登入資訊
			String User_password=jsonin.getString("User_password");
        	Statement stmt = conn.createStatement();
        	//撈資料庫確認登入資訊	
        	String sql = "SELECT * FROM `user` WHERE User_ID = '"+User_ID+"' && User_password = '"+User_password+"'";
        	//ResultSet rs = stmt.executeQuery("SELECT * FROM `user` WHERE User_ID = '"+User_ID+"' && User_password = '"+User_password+"'"  );	            	
        	ResultSet rs = SQL.select(sql);
        	//if(rs.next()) { //如果資料庫有這組帳密       	
        	
        	if(rs!=null) {
        		System.out.println(rs.getString("User_ID"));
        		Server.socketlist.put(User_ID,server);//加進socklist
        		Server.userlist.put(server,User_ID);
        		jsonout.put("Data_name","Login_Success");
        		//{"Data_name":"Login_Success"}       		
        	}
        	else {
        		jsonout.put("Data_name","Login_Error");
        		//{"Data_name":"Login_Error"}
        	}
        	str = jsonout.toString();
        	System.out.println(str);
        	b=str.getBytes();
        	out.write(b);
        	
        	if(jsonout.getString("Data_name").equals("Login_Success")) {
        		Thread.sleep(1000);
        		Load_schedule(server);
            	Load_asset(server); 
            	Load_mission(server);          	
            	Load_build(server);
            	System.out.println("load OK");
        	}        	       	
        } 
		catch (SQLException ex) {
            // handle the error
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	static int Count_column(String User_ID) {
		 String sql = "SELECT * FROM `land` WHERE User_ID = '"+User_ID+"'";
	     ResultSet rs = SQL.select(sql);
	     try {
			rs.isLast();
			int count = rs.getRow();
			return count;
		} catch (SQLException e) {
			System.out.println("Login_74");
			e.printStackTrace();
		}
	     System.out.println("Login_77");
	     return 0;
	}
	static void Load_build(Socket server) throws IOException,  InterruptedException, SQLException {
		String User_ID = Server.userlist.get(server);
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        
        byte b[] = new byte[1024];                   
        
        String sql = "SELECT * FROM `land` WHERE User_ID = '"+User_ID+"'";
        ResultSet rs = SQL.select(sql);
        
        int Build_target = 0;
        
		if(rs==null) {		
			Thread.sleep(200);
			jsonout.put("Data_name","Load_build_zero");
			String str = jsonout.toString();
            System.out.println(str);
            b=str.getBytes();
            out.write(b);
            System.out.println("send");
		}
		else {
			rs.last();
			Build_target = rs.getRow();
			rs.first();	
			Thread.sleep(1000);			
	        do{
	    		Thread.sleep(200);
	        	if(rs!=null) {
	            	String Build_name = rs.getString("Build_name");
	            	int Build_time = rs.getInt("Build_time");
	            	int X = rs.getInt("X");
	            	int Y = rs.getInt("Y");
	            	int production = rs.getInt("Build_production");
	            	
	            	jsonout.put("Data_name","Load_build");
	            	jsonout.put("Build_name",Build_name);
	            	jsonout.put("X",X);
	            	jsonout.put("Y",Y);
	            	jsonout.put("Build_time",Build_time);
	            	jsonout.put("Build_production", production);
	            	jsonout.put("Build_target",Build_target);
	            	String str = jsonout.toString();
	                System.out.println(str);
	                b=str.getBytes();
	                out.write(b);
	        	}       	
	        }while(rs.next());		
		}
					
//////////////////////////////////////////////////		
		
	}
	static void Load_asset(Socket server) throws IOException, SQLException, InterruptedException {
		Thread.sleep(200);
		String User_ID = Server.userlist.get(server);
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        
        
        byte b[] = new byte[1024];
        String sql = "SELECT * FROM `asset` WHERE User_ID = '"+User_ID+"'";
        ResultSet rs = SQL.select(sql);
        jsonout.put("Data_name","Load_asset");
        int Money = rs.getInt("Game_money");
        int Metal = rs.getInt("Metal");
        int Wood = rs.getInt("Wood");
        int Stone = rs.getInt("Stone");
        int Food = rs.getInt("Food");    
        int Time = rs.getInt("Time");
        jsonout.put("Money",Money);
        jsonout.put("Metal",Metal);
        jsonout.put("Wood",Wood);
        jsonout.put("Stone",Stone);
        jsonout.put("Food",Food);
        jsonout.put("Time", Time);       
                   	       
    	String str = jsonout.toString();
        System.out.println(str);
        b=str.getBytes();
        out.write(b);
        rs.close();
	}
	static void Load_schedule(Socket server) throws IOException, SQLException {
		String User_ID = Server.userlist.get(server);
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
                
        byte b[] = new byte[1024];
        String sql = "SELECT * FROM `schedule` WHERE User_ID = '"+User_ID+"'";
        ResultSet rs = SQL.select(sql);
        jsonout.put("Data_name","Load_schedule");
        String Already_movie = rs.getString("Already_movie");
        String Period_name = rs.getString("Period_name");
        String PassLevel = rs.getString("PassLevel");

        jsonout.put("Already_movie",Already_movie);
        jsonout.put("Period_name",Period_name);
        jsonout.put("PassLevel",PassLevel);   
        
    	String str = jsonout.toString();
        System.out.println(str);
        b=str.getBytes("UTF-8");
        out.write(b);
        rs.close();
	}
	static void Load_password(Socket server) throws IOException, SQLException {
		String User_ID = Server.userlist.get(server);
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        
        byte b[] = new byte[1024];
        String sql = "SELECT * FROM `asset` WHERE User_ID = '"+User_ID+"'";
        ResultSet rs = SQL.select(sql);   
        rs.close();
	}
	static void Load_mission(Socket server) throws IOException, SQLException, InterruptedException {
		String User_ID = Server.userlist.get(server);
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        byte b[] = new byte[1024];
        
        
        String sql = "SELECT * FROM `Mission_new` WHERE User_ID = '"+User_ID+"' && Mission_status != '0'";
	    ResultSet rs = SQL.select(sql);		    
	    do {
	    	if(rs==null) {		
	    		Thread.sleep(200);
	    		break;
			}
	    	else {
	    		Thread.sleep(200);
		    	jsonout.put("Data_name","Load_mission");
	            String Mission_order = rs.getString("Mission_order");
	            int Mission_status = rs.getInt("Mission_status");
	            
	            jsonout.put("Mission_order",Mission_order);
	            jsonout.put("Mission_status",Mission_status);
	            
	            String str = jsonout.toString();
	            System.out.println(str);
	            b=str.getBytes("UTF-8");
	            out.write(b);
	    	}	    	
	    }while(rs.next());        
	}
}