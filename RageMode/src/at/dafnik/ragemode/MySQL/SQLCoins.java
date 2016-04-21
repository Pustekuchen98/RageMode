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
			Main.mysql.update("INSERT INTO Coins(UUID, COINS, SPEEDUPGRADE, BOWPOWERUPGRADE, KNOCKBACKUPGRADE, SPECTRALARROWUPGRADE, DOUBLEPOWERUPGRADE) VALUES ('" + uuid + "', '0', '0', '0', '0', '0', '0');");
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
	
	public static Boolean getSpeedUpgrade(String uuid) {
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
		
		if(i == 0) return false;
		else return true;
	}
	
	public static void setSpeedUpgrade(String uuid, Boolean in) {
		if(playerExists(uuid)) {
			int i;
			if(in) i = 1;
			else i = 0;
			Main.mysql.update("UPDATE Coins SET SPEEDUPGRADE= '" + i + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setSpeedUpgrade(uuid, in);
		}
	}
	
	//-------------------------------------------------Speed-------------------------------------------------
	
	public static Boolean getBowPowerUpgrade(String uuid) {
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
		
		if(i == 0) return false;
		else return true;
	}
	
	public static void setBowPowerUpgrade(String uuid, Boolean in) {
		if(playerExists(uuid)) {
			int i;
			if(in) i = 1;
			else i = 0;
			Main.mysql.update("UPDATE Coins SET BOWPOWERUPGRADE= '" + i + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setBowPowerUpgrade(uuid, in);
		}
	}
	
	//--------------------------------------------------Knockback Ability-------------------------------------------------
	
	public static Boolean getKnockbackUpgrade(String uuid) {
		Integer i = 0;
			
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("KNOCKBACKUPGRADE")) == null));
				
				i = rs.getInt("KNOCKBACKUPGRADE");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getKnockbackUpgrade(uuid);
		}
		
		if(i == 0) return false;
		else return true;
	}
	
	public static void setKnockbackUpdgrade(String uuid, Boolean in) {
		if(playerExists(uuid)) {
			int i;
			if(in) i = 1;
			else i = 0;
			Main.mysql.update("UPDATE Coins SET KNOCKBACKUPGRADE= '" + i + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setKnockbackUpdgrade(uuid, in);
		}
	}
	
	// --------------------------------------------------Spectral
	// Ability-------------------------------------------------

	public static Boolean getSpectralArrowUpgrade(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("SPECTRALARROWUPGRADE")) == null))
					;

				i = rs.getInt("SPECTRALARROWUPGRADE");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			createPlayer(uuid);
			getSpectralArrowUpgrade(uuid);
		}

		if (i == 0)
			return false;
		else
			return true;
	}

	public static void setSpectralArrowUpgrade(String uuid, Boolean in) {
		if (playerExists(uuid)) {
			int i;
			if (in)
				i = 1;
			else
				i = 0;
			Main.mysql.update("UPDATE Coins SET SPECTRALARROWUPGRADE= '" + i + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setSpectralArrowUpgrade(uuid, in);
		}
	}
	
	// --------------------------------------------------Double PowerUP-------------------------------------------------

	public static Boolean getDoublePowerUP(String uuid) {
		Integer i = 0;

		if (playerExists(uuid)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Coins WHERE UUID= '" + uuid + "'");
				if ((!rs.next()) || (Integer.valueOf(rs.getInt("DOUBLEPOWERUPGRADE")) == null))
					;

				i = rs.getInt("DOUBLEPOWERUPGRADE");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			createPlayer(uuid);
			getDoublePowerUP(uuid);
		}

		if (i == 0)
			return false;
		else
			return true;
	}

	public static void setDoublePowerUP(String uuid, Boolean in) {
		if (playerExists(uuid)) {
			int i;
			if (in)
				i = 1;
			else
				i = 0;
			Main.mysql.update("UPDATE Coins SET DOUBLEPOWERUPGRADE= '" + i + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setDoublePowerUP(uuid, in);
		}
	}
}
