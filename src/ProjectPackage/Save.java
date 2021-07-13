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

public class Save {
	static void Save_asset(Socket server,JSONObject jsonin) throws SQLException {
		String UserID = Server.userlist.get(server);
		//之後要改money分兩種  20210524
		String Game_money = jsonin.getString("Money") ;
		String Virtual_money = "0";
		String Metal = jsonin.getString("Metal");
		String Wood = jsonin.getString("Wood");
		String Stone = jsonin.getString("Stone");
		String Food = jsonin.getString("Food");
		String Tool = "0";
		String Time = jsonin.getString("Time");
		String sql;
		//看db裡有沒有資料 如果有的話用update 沒有的話要用insert
		if(checkAssetFromDB(UserID)==1) {
			sql = "UPDATE `asset` SET `Game_money`='"+Game_money+"',`Virtual_money`='"+Virtual_money+"',`Metal`='"+Metal+"',`Wood`='"+Wood+"',`Stone`='"+Stone+"',`Food`='"+Food+"',`Tool`='"+Tool+"', `Time` = '"+Time+"' WHERE `User_ID`= '"+UserID+"'";			
		}
		else {
			sql = "INSERT INTO `asset`(`User_ID`, `Game_money`, `Virtual_money`, `Metal`, `Wood`, `Stone`, `Food`, `Tool`, `Time`) VALUES ('"+UserID+"','"+Game_money+"','"+Virtual_money+"','"+Metal+"','"+Wood+"','"+Stone+"','"+Food+"','"+Tool+"','"+Time+"')";
		}		
	    SQL.insert_update(sql);
	}
	//
	static void Save_building(Socket server,JSONObject jsonin) {
		String UserID = Server.userlist.get(server);
		
		String X = jsonin.getString("X");
		String Y = jsonin.getString("Y");
		String production = jsonin.getString("production");
		String sql;
		
		sql = "UPDATE `land` SET `Build_production`='"+production+"' WHERE `User_ID`='"+UserID+"' && `X`='"+X+"' && `Y`='"+Y+"'";
			
	    SQL.insert_update(sql);
	}
	
	static int checkAssetFromDB(String UserID) throws SQLException {
		//看db裡有沒有資料 如果有的話return 1 沒有的話return 0 
	    String sql = "SELECT * FROM `asset` WHERE `User_ID`= '"+UserID+"'";
		ResultSet rs = SQL.select(sql);
		
		if(rs!=null) {
			return 1;
		}
		return 0;
	}
	/*
	static int checkBuildingFromDB(String UserID,String X,String Y) {
		String sql = "SELECT * FROM `land` WHERE `User_ID` ='"+UserID+"' && `X`='"+X+"' && `Y`='"+Y+"'";
		ResultSet rs = SQL.select(sql);
		
		if(rs!=null) {
			return 1;
		}
		return 0;
	}
	 */	
}

