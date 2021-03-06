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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create_Account {

	static void CreateAccount(Socket server,JSONObject jsonin) throws IOException, JSONException, SQLException, InterruptedException {
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
        String str = new String(); 
        JSONObject jsonout = new JSONObject();
        byte b[] = new byte[1024];
		
		String User_ID = (String) jsonin.get("User_ID");
		String User_password = (String)jsonin.get("User_password");
		String sql = "SELECT * FROM user WHERE User_ID = '"+User_ID+"'";
		
		ResultSet rs = SQL.select(sql);
		if(rs!=null) { //撈的到東西的話就是有人使用這個User_ID，傳回Error
			//失敗回傳CreatAccount_Error
			jsonout.put("Data_name","CreatAccount_Error");
		}
		else {
			sql = "INSERT INTO `user`(`User_ID`, `User_password`) "
					+ "VALUES ('"+User_ID+"','"+User_password+"')";			
			SQL.insert_update(sql);
			sql = "INSERT INTO `asset`(`User_ID`, `Game_money`, `Virtual_money`, `Metal`, `Wood`, `Stone`, `Food`, `Tool`, `Time`) VALUES ('"+User_ID+"','0','0','0','0','0','0','0','0')";
			SQL.insert_update(sql);
			sql = "INSERT INTO `schedule`(`User_ID`, `Already_movie`, `Period_name`, `PassLevel`) VALUES ('"+User_ID+"','0','貝殼時期','0')";
			SQL.insert_update(sql);
			for(int i=1;i<=7;i++) {
				sql = "INSERT INTO `mission_new`(`User_ID`, `Mission_order`, `Mission_status`) VALUES ('"+User_ID+"','"+i+"','0')";
				SQL.insert_update(sql);
			}			
			//成功回傳CreatAccount_Success讓頁面可以跳轉
			jsonout.put("Data_name","CreatAccount_Success");
		}
		System.out.println(jsonout.getString("Data_name"));
		str = jsonout.toString();	
        b=str.getBytes();
        out.write(b);
        //rs.close();
	}
	
}

