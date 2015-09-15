package me.bigblaster10.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;



public class MySQL {
	

	public static String user = "root";
	public static String pass;
	public static String url = "jdbc:mysql://localhost/cubeworld"; 	
	
	
	public static Connection conn;   
	
	
	public static ResultSet executeQuery(String query){
		 try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();

		}  
		 return null;
	}
	
	public static void prepareStatement(String query){
		 try {
			PreparedStatement pStmt = conn.prepareStatement(query);
			pStmt.executeUpdate();
			pStmt.close();   
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}	

	public static boolean next(ResultSet rs){
		 try {
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}  
		 return false;
	}
	
	
	
	public static String getString(ResultSet rs, String string){
		ResultSet rs1 = rs;
		 try {			
			return rs1.getString(string);			
		} catch (SQLException e) {
			e.printStackTrace();

		}  
		 return null;
	}
	
	public static int getInt(ResultSet rs, String string){
		ResultSet rs1 = rs;

		 try {			
				return rs1.getInt(string);
			
		} catch (SQLException e) {
			e.printStackTrace();

		}  

		 return 0;
	}
	
	public static boolean getBoolean(ResultSet rs, String string){
		ResultSet rs1 = rs;

		 try {			
				return rs1.getBoolean(string);
			
		} catch (SQLException e) {
			e.printStackTrace();

		}  

		 return false;	
	}
	
	
	
	
	public static void createConnection(){
		
		
		//getpassword
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("plugins/CubeWorld/mysql.txt"));			
			pass = br.readLine();		
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Bukkit.broadcastMessage("ERROR: MYSQL PASSWORD");
			e1.printStackTrace();
		}
		
		
		
		try {
			MySQL.conn = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.pass);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static boolean isConnected(){
		if(conn == null) return false;
		try {
			return !conn.isClosed();
		} catch (SQLException e) {
			Bukkit.broadcastMessage(ChatColor.RED + "ERROR: MYSQL CONNECTION FAILED");
			e.printStackTrace();
		}
		return false;
	}
	
}
