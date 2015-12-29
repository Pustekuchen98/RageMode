package at.dafnik.ragemode.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.dafnik.ragemode.Main.Main;

public class SQLCoins {
	
	public static boolean playerExists(String uuid) {
		
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
			
			if(rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return false;
	}
	
	public static void createPlayer(String uuid) {
		
		if(!(playerExists(uuid))){
			Main.mysql.update("INSERT INTO Coins(UUID, COINS, SPEEDUPGRADE, BOWPOWERUPGRADE) VALUES ('" + uuid + "', '0', '0', '0');");
		}
	}
	
	//-------------------------------------------COINS-------------------------------------------------
	
	public static Integer getCoins(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("COINS")) == null));
				
				i = rs.getInt("COINS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getCoins(uuid);
		}
		return i;
	}
	
	public static void setCoins(String uuid, Integer coins) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Coins SET COINS= '" + coins + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setCoins(uuid, coins);
		}
	}
	
	public static void addCoins(String uuid, Integer coins){
		if(playerExists(uuid)) {
			setCoins(uuid, Integer.valueOf(getCoins(uuid).intValue() + coins.intValue()));
		} else {
			createPlayer(uuid);
			addCoins(uuid, coins);
		}
	}
	
	public static void removeCoins(String uuid, Integer coins){
		if(playerExists(uuid)) {
			setCoins(uuid, Integer.valueOf(getCoins(uuid).intValue() - coins.intValue()));
		} else {
			createPlayer(uuid);
			removeCoins(uuid, coins);
		}
	}
	
	//-------------------------------------------------Speed-------------------------------------------------
	
	public static Integer getSpeedUpgrade(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("SPEEDUPGRADE")) == null));
				
				i = rs.getInt("SPEEDUPGRADE");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getSpeedUpgrade(uuid);
		}
		return i;
	}
	
	public static void setSpeedUpgrade(String uuid, Integer speed) {
		if(playerExists(uuid)) {
			if(speed > 1) speed = 1;
			if(speed < 0) speed = 0;
			Main.mysql.update("UPDATE Coins SET SPEEDUPGRADE= '" + speed + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setSpeedUpgrade(uuid, speed);
		}
	}
	
	//-------------------------------------------------Speed-------------------------------------------------
	
	public static Integer getBowPowerUpgrade(String uuid) {
		Integer i = 0;
			
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("BOWPOWERUPGRADE")) == null));
				
				i = rs.getInt("BOWPOWERUPGRADE");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getBowPowerUpgrade(uuid);
		}
		return i;
	}
	
	public static void setBowPowerUpgrade(String uuid, Integer i) {
		if(playerExists(uuid)) {
			if(i > 1) i = 1;
			if(i < 0) i = 0;
			Main.mysql.update("UPDATE Coins SET BOWPOWERUPGRADE= '" + i + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setBowPowerUpgrade(uuid, i);
		}
	}//------------------------------------------------------------------------------------------------------------
}
