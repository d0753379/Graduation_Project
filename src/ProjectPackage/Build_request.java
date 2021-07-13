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
		
		//�qButton_name���ΥXX,Y
		String X = Button_name.replaceAll("[A-Za-z_.() ]","");
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
	    System.out.println(Y);
	    //X,Y���Χ�
		JSONObject jsonout = new JSONObject();
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        String str = new String(); 
        byte b[] = new byte[1024];
        String sql = "SELECT * FROM `land` WHERE User_ID = '"+User_ID+"' && `X` = '"+X+"' && `Y`= '"+Y+"'";
        //�Ĥ@������Ʈw�T�{�Ӥg�a(X,Y)���S���Q�سy�F��F
        ResultSet rs = SQL.select(sql);	            	
        if(rs!=null) { //�p�G��Ʈw��XY�b�W���a�Ϫ�ܿ��~
        	jsonout.put("Data_name","Build_Error");
        	//{"Data_name":"Build_Error"}
        }
        else {
        	jsonout.put("Data_name","Build_Success");
        }
        //��X,Y�b�ijson
    	jsonout.put("X", X);
    	jsonout.put("Y", Y);
        str = jsonout.toString();
        System.out.println(str);
        b=str.getBytes();
        out.write(b);	        		
	}
	static void Build(Socket server,JSONObject jsonin){
		String User_ID = Server.userlist.get(server);
		String Build_name = jsonin.getString("Build_name");
		int Build_time = jsonin.getInt("Build_time");
		int X = jsonin.getInt("X");
		int Y = jsonin.getInt("Y");
		String sql = "INSERT INTO `land`(`User_ID`, `Build_name`, `X`, `Y`, `Build_time`) VALUES ('"+User_ID+"','"+Build_name+"','"+X+"','"+Y+"','"+Build_time+"')";
		SQL.insert_update(sql);
	}
}
