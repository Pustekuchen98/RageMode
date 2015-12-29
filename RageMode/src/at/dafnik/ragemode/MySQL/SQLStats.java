package at.dafnik.ragemode.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.dafnik.ragemode.Main.Main;

public class SQLStats {
	
	public static boolean playerExists(String uuid) {
		
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
			
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
			Main.mysql.update("INSERT INTO Stats(UUID, KILLS, DEATHS, PLAYEDGAMES, WONGAMES, POINTS, RESETS, BOWKILLS, AXTKILLS, KNIFEKILLS, SUICIDES) VALUES ('" + uuid + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
		}
	}
	
	//--------------------------------------------------Kills------------------------------------------------------------------
	
	public static Integer getKills(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null));
				
				i = rs.getInt("KILLS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getKills(uuid);
		}
		return i;
	}
	
	public static void setKills(String uuid, Integer kills) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET KILLS= '" + kills + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setKills(uuid, kills);
		}
	}
	
	public static void addKills(String uuid, Integer kills){
		if(playerExists(uuid)) {
			setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
		} else {
			createPlayer(uuid);
			addKills(uuid, kills);
		}
	}
	
	public static void removeKills(String uuid, Integer kills){
		if(playerExists(uuid)) {
			setKills(uuid, Integer.valueOf(getKills(uuid).intValue() - kills.intValue()));
		} else {
			createPlayer(uuid);
			removeKills(uuid, kills);
		}
	}
	
	//--------------------------------------------------Deaths------------------------------------------------------------
	
	public static Integer getDeaths(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("DEATHS")) == null));
				
				i = rs.getInt("DEATHS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getDeaths(uuid);
		}
		return i;
	}
	
	public static void setDeaths(String uuid, Integer deaths) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET DEATHS= '" + deaths + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setDeaths(uuid, deaths);
		}
	}
	
	
	
	public static void addDeaths(String uuid, Integer deaths){
		if(playerExists(uuid)) {
			setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + deaths.intValue()));
		} else {
			createPlayer(uuid);
			addDeaths(uuid, deaths);
		}
	}
	
	
	
	public static void removeDeaths(String uuid, Integer deaths){
		if(playerExists(uuid)) {
			setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() - deaths.intValue()));
		} else {
			createPlayer(uuid);
			removeDeaths(uuid, deaths);
		}
	}
	
	//-----------------------------Played Games--------------------------------------------------------------
	
	public static Integer getPlayedGames(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("PLAYEDGAMES")) == null));
				
				i = rs.getInt("PLAYEDGAMES");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getPlayedGames(uuid);
		}
		return i;
	}
	
	public static void setPlayedGames(String uuid, Integer playedgames) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET PLAYEDGAMES= '" + playedgames + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setPlayedGames(uuid, playedgames);
		}
	}
	
	
	
	public static void addPlayedGames(String uuid, Integer playedgames){
		if(playerExists(uuid)) {
			setPlayedGames(uuid, Integer.valueOf(getPlayedGames(uuid).intValue() + playedgames.intValue()));
		} else {
			createPlayer(uuid);
			addPlayedGames(uuid, playedgames);
		}
	}
	
	
	
	public static void removePlayedGames(String uuid, Integer playedgames){
		if(playerExists(uuid)) {
			setPlayedGames(uuid, Integer.valueOf(getPlayedGames(uuid).intValue() - playedgames.intValue()));
		} else {
			createPlayer(uuid);
			removePlayedGames(uuid, playedgames);
		}
	}
	
	//----------------------------------------Won Games--------------------------------------------------------
	
	public static Integer getWonGames(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("WONGAMES")) == null));
				
				i = rs.getInt("WONGAMES");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getWonGames(uuid);
		}
		return i;
	}
	
	public static void setWonGames(String uuid, Integer wongames) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET WONGAMES= '" + wongames + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setWonGames(uuid, wongames);
		}
	}
	
	
	
	public static void addWonGames(String uuid, Integer wongames){
		if(playerExists(uuid)) {
			setWonGames(uuid, Integer.valueOf(getWonGames(uuid).intValue() + wongames.intValue()));
		} else {
			createPlayer(uuid);
			addWonGames(uuid, wongames);
		}
	}
	
	
	
	public static void removeWonGames(String uuid, Integer wongames){
		if(playerExists(uuid)) {
			setWonGames(uuid, Integer.valueOf(getWonGames(uuid).intValue() - wongames.intValue()));
		} else {
			createPlayer(uuid);
			removeWonGames(uuid, wongames);
		}
	}
	
	//--------------------------------------------------Points------------------------------------------------------------------
	
	public static Integer getPoints(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("POINTS")) == null));
				
				i = rs.getInt("POINTS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getPoints(uuid);
		}
		return i;
	}
	
	public static void setPoints(String uuid, Integer points) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET POINTS= '" + points + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setPoints(uuid, points);
		}
	}
	
	public static void addPoints(String uuid, Integer points){
		if(playerExists(uuid)) {
			setPoints(uuid, Integer.valueOf(getPoints(uuid).intValue() + points.intValue()));
		} else {
			createPlayer(uuid);
			addPoints(uuid, points);
		}
	}
	
	public static void removePoints(String uuid, Integer points){
		if(playerExists(uuid)) {
			setPoints(uuid, Integer.valueOf(getPoints(uuid).intValue() - points.intValue()));
		} else {
			createPlayer(uuid);
			removePoints(uuid, points);
		}
	}
	
	//--------------------------------------------RESETS------------------------------------------------------
	
	public static Integer getResets(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("RESETS")) == null));
				
				i = rs.getInt("RESETS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getResets(uuid);
		}
		return i;
	}
	
	public static void setResets(String uuid, Integer resets) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET RESETS= '" + resets + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setResets(uuid, resets);
		}
	}
	
	public static void addResets(String uuid, Integer resets){
		if(playerExists(uuid)) {
			setResets(uuid, Integer.valueOf(getResets(uuid).intValue() + resets.intValue()));
		} else {
			createPlayer(uuid);
			addResets(uuid, resets);
		}
	}
	
	public static void removeResets(String uuid, Integer resets){
		if(playerExists(uuid)) {
			setResets(uuid, Integer.valueOf(getResets(uuid).intValue() - resets.intValue()));
		} else {
			createPlayer(uuid);
			removeResets(uuid, resets);
		}
	}
	
	//--------------------------------------------BOWKILLS------------------------------------------------------
	
	public static Integer getBowKills(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("BOWKILLS")) == null));
				
				i = rs.getInt("BOWKILLS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getBowKills(uuid);
		}
		return i;
	}
	
	public static void setBowKills(String uuid, Integer in) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET BOWKILLS= '" + in + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setBowKills(uuid, in);
		}
	}
	
	public static void addBowKills(String uuid, Integer in){
		if(playerExists(uuid)) {
			setBowKills(uuid, Integer.valueOf(getBowKills(uuid).intValue() + in.intValue()));
		} else {
			createPlayer(uuid);
			addBowKills(uuid, in);
		}
	}
	
	public static void removeBowKills(String uuid, Integer in){
		if(playerExists(uuid)) {
			setBowKills(uuid, Integer.valueOf(getBowKills(uuid).intValue() - in.intValue()));
		} else {
			createPlayer(uuid);
			removeBowKills(uuid, in);
		}
	}
	
	//--------------------------------------------AXTKILLS------------------------------------------------------
	
	public static Integer getAxtKills(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("AXTKILLS")) == null));
				
				i = rs.getInt("AXTKILLS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getAxtKills(uuid);
		}
		return i;
	}
	
	public static void setAxtKills(String uuid, Integer in) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET AXTKILLS= '" + in + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setAxtKills(uuid, in);
		}
	}
	
	public static void addAxtKills(String uuid, Integer in){
		if(playerExists(uuid)) {
			setAxtKills(uuid, Integer.valueOf(getAxtKills(uuid).intValue() + in.intValue()));
		} else {
			createPlayer(uuid);
			addAxtKills(uuid, in);
		}
	}
	
	public static void removeAxtKills(String uuid, Integer in){
		if(playerExists(uuid)) {
			setAxtKills(uuid, Integer.valueOf(getAxtKills(uuid).intValue() - in.intValue()));
		} else {
			createPlayer(uuid);
			removeAxtKills(uuid, in);
		}
	}
	
	//--------------------------------------------KNIFEKILLS------------------------------------------------------
	
	public static Integer getKnifeKills(String uuid) {
		Integer i = 0;
		
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("KNIFEKILLS")) == null));
				
				i = rs.getInt("KNIFEKILLS");
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		} else {
			createPlayer(uuid);
			getKnifeKills(uuid);
		}
		return i;
	}
	
	public static void setKnifeKills(String uuid, Integer in) {
		if(playerExists(uuid)) {
			Main.mysql.update("UPDATE Stats SET KNIFEKILLS= '" + in + "' WHERE UUID= '" + uuid + "';");
		} else {
			createPlayer(uuid);
			setKnifeKills(uuid, in);
		}
	}
	
	public static void addKnifeKills(String uuid, Integer in){
		if(playerExists(uuid)) {
			setKnifeKills(uuid, Integer.valueOf(getKnifeKills(uuid).intValue() + in.intValue()));
		} else {
			createPlayer(uuid);
			addKnifeKills(uuid, in);
		}
	}
	
	public static void removeKnifeKills(String uuid, Integer in){
		if(playerExists(uuid)) {
			setKnifeKills(uuid, Integer.valueOf(getKnifeKills(uuid).intValue() - in.intValue()));
		} else {
			createPlayer(uuid);
			removeKnifeKills(uuid, in);
		}
	}
	
	//--------------------------------------------SUICIDES------------------------------------------------------
	
		public static Integer getSuicides(String uuid) {
			Integer i = 0;
			
			if(playerExists(uuid)){
				try {
					ResultSet rs = Main.mysql.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
					if((!rs.next()) || (Integer.valueOf(rs.getInt("SUICIDES")) == null));
					
					i = rs.getInt("SUICIDES");
				} catch (SQLException e) {
					e.printStackTrace();
				}	
				
			} else {
				createPlayer(uuid);
				getSuicides(uuid);
			}
			return i;
		}
		
		public static void setSuicides(String uuid, Integer in) {
			if(playerExists(uuid)) {
				Main.mysql.update("UPDATE Stats SET SUICIDES= '" + in + "' WHERE UUID= '" + uuid + "';");
			} else {
				createPlayer(uuid);
				setSuicides(uuid, in);
			}
		}
		
		public static void addSuicides(String uuid, Integer in){
			if(playerExists(uuid)) {
				setSuicides(uuid, Integer.valueOf(getSuicides(uuid).intValue() + in.intValue()));
			} else {
				createPlayer(uuid);
				addSuicides(uuid, in);
			}
		}
		
		public static void removeSuicides(String uuid, Integer in){
			if(playerExists(uuid)) {
				setSuicides(uuid, Integer.valueOf(getSuicides(uuid).intValue() - in.intValue()));
			} else {
				createPlayer(uuid);
				removeSuicides(uuid, in);
			}
		}
}
