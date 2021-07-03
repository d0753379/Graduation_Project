package ProjectPackage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

public class Build_request {

	static void Build_check(Socket server,JSONObject jsonin) throws IOException{
		String User_ID = Server.userlist.get(server);
		String Button_name = jsonin.getString("Button_name");
		
		//從Button_name切割出X,Y
		String X = Button_name.replaceAll("[A-Za-z_]","");
		String[] temp = X.split("");
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < temp.length; i++) {
	         sb.append(temp[i]);
	    }
	    X = sb.toString();
	    temp = X.split("");
	    sb = new StringBuffer();
	    for(int i = 0; i < temp.length; i++) {
	         sb.append(temp[i]);
	    }
	    X = sb.toString();
	    String Y = X.substring(X.indexOf('-')+1, X.length());
	    X = X.substring(0, X.indexOf('-'));
	    //X,Y切割完
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        String str = new String(); 
        byte b[] = new byte[1024];
        String sql = "SELECT * FROM `land` WHERE User_ID = '"+User_ID+"' && `X` = '"+X+"' && `Y`= '"+Y+"'";
        //第一次撈資料庫確認該土地(X,Y)有沒有被建造東西了
        ResultSet rs = SQL.select(sql);	            	
        if(rs!=null) { //如果資料庫的XY軸上有地圖表示錯誤
        	jsonout.put("Data_name","Build_Error");
        	//{"Data_name":"Build_Error"}
        }
        else {
        	//回傳建造這個建築物，且記錄到資料庫
        	jsonout.put("Data_name","Build_Success");
        	/*第二次撈資料庫確認該建築有沒有存在
        	sql = "SELECT `User_ID`, `Build_name`, `X`, `Y` FROM `land` WHERE User_ID = '"+User_ID+"' && Build_name = '"+Build_name+"'";
        	rs = SQL.select(sql);
        	if(rs!=null) {
        		jsonout.put("Data_name","Build_Error");
            	//{"Data_name":"Build_Error"}
        	}
        	else {
        		//回傳建造這個建築物，且記錄到資料庫
            	//{"Data_name":"Build_Success"}
            	jsonout.put("Data_name","Build_Success");
            	Build_build(server,jsonin);
        	}
        	*/
        }
        //放X,Y軸進json
    	jsonout.put("X", X);
    	jsonout.put("Y", Y);
        str = jsonout.toString();	
        b=str.getBytes();
        out.write(b);	        		
	}
	static void Build(Socket server,JSONObject jsonin){
		String User_ID = Server.userlist.get(server);
		String Build_name = jsonin.getString("Build_name");
		String Build_time = jsonin.getString("Build_time");
		String X = jsonin.getString("X");
		String Y = jsonin.getString("Y");
		String sql = "INSERT INTO `land`(`User_ID`, `Build_name`, `X`, `Y`, `Build_time`) VALUES ('"+User_ID+"','"+Build_name+"','"+X+"','"+Y+"','"+Build_time+"')";
		SQL.insert_update(sql);
	}
}
