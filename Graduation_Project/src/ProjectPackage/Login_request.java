package ProjectPackage;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
        	ResultSet rs = stmt.executeQuery("SELECT * FROM `user` WHERE User_ID = '"+User_ID+"' && User_password = '"+User_password+"'"  );	            	
        	if(rs.next()) { //如果資料庫有這組帳密
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
        	b=str.getBytes();
        	out.write(b);
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
