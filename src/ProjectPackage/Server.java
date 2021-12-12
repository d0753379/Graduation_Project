package ProjectPackage;

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

public class Server extends Thread implements Runnable {
	private Socket server;
	static List<Socket> socket = new ArrayList<Socket>();
	// key-value 用User_ID找socket
	static HashMap<String, Socket> socketlist = new HashMap<String, Socket>();
	// key-value 用socket找User_ID
	static HashMap<Socket, String> userlist = new HashMap<Socket, String>();
	// byte jsonout[] = new byte[1024];

	Server(Socket server) {
		this.server = server;
	}

	public void run() {
		boolean go = true;
	
		while (go) {
			try {
				server.getKeepAlive();
				DataInputStream inputStream = null;// 收JSON
				DataOutputStream outputStream = null;// 送圖片byte
				DataOutputStream out = null;
				String strInputstream = "";
				String dataName = "";

				inputStream = new DataInputStream(server.getInputStream());
				byte[] by = new byte[2048];
				inputStream.read(by);// JSON型態是byte所以用byte收
				
				strInputstream = new String(by);// byte轉String
				
				//斷線
				if(!strInputstream.startsWith("{")){
					//System.out.println(strInputstream);
					System.out.println(server.getInetAddress().toString()+"_out");
					socketlist.remove(userlist.get(server));
					userlist.remove(server);
					break;
				}
				
				// server.shutdownInput();
				// inputStream.close();
				// baos.close();
				// 將socket接受到的數據(byte)還原為JSONObject
				System.out.println(strInputstream);
				JSONObject jsonin = new JSONObject(strInputstream);

				System.out.println(jsonin.toString());
				dataName = (String) jsonin.get("Data_name");

				switch (dataName) {
					case "Login_request":
						Login_request.Login(server, jsonin);
						break;
					case "Save_asset":
						Save.Save_asset(server, jsonin);
						break;
					case "Save_building":
						Save.Save_building(server, jsonin);
						break;
					case "Save_schedule":
						Save.Save_schedule(server, jsonin);
						break;
					case "Save_mission":
						Save.Save_mission(server, jsonin);
						break;
					case "Build_check":
						Build_request.Build_check(server, jsonin);
						break;
					case "Build_request":
						Build_request.Build(server,jsonin);
						break;
					case "CreatAccount_request":
						Create_Account.CreateAccount(server,jsonin);
						break;
					default:
						System.out.println("Error!");
						break;
				}
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 5678;
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			try {
				System.out.println("等待連接...");
				Socket server = serverSocket.accept();
				String clientIP=server.getInetAddress().toString();
				System.out.println(clientIP+"已連線");
				socket.add(server);
				Thread t = new Server(server);
				t.start();
				System.out.println("連線成功!!");
			} catch (IOException e) {
				e.printStackTrace();
				serverSocket.close();
			}
		}
	}
}
