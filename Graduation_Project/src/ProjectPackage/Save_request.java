package ProjectPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

import java.sql.ResultSet;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.*;

public class Save_request {
	static void save(JSONObject jsonin) {
		String UserID = jsonin.getString("User_ID");
		String Game_money = jsonin.getString("Game_money") ;
		String Virtual_money = jsonin.getString("Virtual_money");
		String Metal = jsonin.getString("Metal");
		String Wood = jsonin.getString("Wood");
		String Stone = jsonin.getString("Stone");
		String Food = jsonin.getString("Food");
		String Tool = jsonin.getString("Tool");
		String sql;
		//看db裡有沒有資料 如果有的話用update 沒有的話要用insert
		if(checkFromDB(UserID)==1) {
			sql = "UPDATE `asset` SET `Game_money`='"+Game_money+"',`Virtual_money`='"+Virtual_money+"',`Metal`='"+Metal+"',`Wood`='"+Wood+"',`Stone`='"+Stone+"',`Food`='"+Food+"',`Tool`='"+Tool+"' WHERE `User_ID`= '"+UserID+"'";			
		}
		else {
			sql = "INSERT INTO `asset`(`User_ID`, `Game_money`, `Virtual_money`, `Metal`, `Wood`, `Stone`, `Food`, `Tool`) VALUES ('"+UserID+"','"+Game_money+"','"+Virtual_money+"','"+Metal+"','"+Wood+"','"+Stone+"','"+Food+"','"+Tool+"')";
		}
	    SQL.insert_update(sql);
	}
	//
	static int checkFromDB(String UserID) {
		//看db裡有沒有資料 如果有的話return 1 沒有的話return 0 
	    String sql = "SELECT * FROM `asset` WHERE `User_ID`= '"+UserID+"'";
		ResultSet rs = SQL.select(sql);
		if(rs!=null) {
			return 1;
		}
		return 0;
	}
}

