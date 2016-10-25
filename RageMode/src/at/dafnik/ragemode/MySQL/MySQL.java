package at.dafnik.ragemode.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import at.dafnik.ragemode.API.Strings;

public class MySQL {
	
	private String HOST = "";
	private String DATABASE = "";
	private String USER = "";
	private String PASSWORD = "";
	
	private Connection con;
	
	public MySQL(String host, String database, String user, String password) {
		this.HOST = host;
		this.DATABASE = database;
		this.USER = user;
		this.PASSWORD = password;
		
		connect();
	}

	public void connect() {
		try {
			con =  DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
			System.out.println(Strings.ragemode_mysql_connected);
		} catch (SQLException e){
			System.out.println(Strings.error_could_not_connect_to_mysql);
		}
	}
	
	public void close() {
		try {
			if(con != null) {
				con.close();
				System.out.println(Strings.ragemode_mysql_disconnected);
			}
		} catch (SQLException e) {
			System.out.println(Strings.error_could_not_disconnect_to_mysql);
		}
	}
	
	public void update(String qry) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
	}
	
	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
		return rs;
	}
	
}
