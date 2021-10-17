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

	static void CreateAccount(Socket server,JSONObject jsonin) throws IOException, JSONException, SQLException {
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
        String str = new String(); 
        JSONObject jsonout = new JSONObject();
        byte b[] = new byte[1024];
		
		String User_ID = (String) jsonin.get("User_ID");
		String User_password = (String)jsonin.get("User_password");
		String sql = "SELECT * FROM user WHERE User_ID = '"+User_ID+"'";
		
		ResultSet rs = SQL.select(sql);
		if(rs!=null) { //撈的到東西的話就是有人使用這個User_ID，傳回Error
			jsonout.put("Data_name","CreatAccount_Error");//失敗回傳CreatAccount_Error
		}
		else {
			sql = "INSERT INTO `user`(`User_ID`, `User_password`) VALUES ('"+User_ID+"','"+User_password+"')";			SQL.insert_update(sql);
			jsonout.put("Data_name","CreatAccount_Success");//成功回傳CreatAccount_Success讓頁面可以跳轉
		}
		System.out.println(jsonout.getString("Data_name"));
		str = jsonout.toString();	
        b=str.getBytes();
        out.write(b);
	}
	
}
