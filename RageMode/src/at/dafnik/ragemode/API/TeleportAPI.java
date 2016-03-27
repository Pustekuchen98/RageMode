package at.dafnik.ragemode.API;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;

public class TeleportAPI {
	
	public static Location getLobbyLocation() {
		String w = Main.getInstance().getConfig().getString("ragemode.lobbyspawn.world");
		double x = Main.getInstance().getConfig().getDouble("ragemode.lobbyspawn.x");
		double y = Main.getInstance().getConfig().getDouble("ragemode.lobbyspawn.y");
		double z = Main.getInstance().getConfig().getDouble("ragemode.lobbyspawn.z");
		double yaw = Main.getInstance().getConfig().getDouble("ragemode.lobbyspawn.yaw");
		double pitch = Main.getInstance().getConfig().getDouble("ragemode.lobbyspawn.pitch");
		
		if(w != null) {
			Location loc = new Location(Bukkit.getWorld(w), x, y, z);
			loc.setYaw((float) yaw);
			loc.setPitch((float) pitch);
			return loc;
		} else {
			return null;
		}
	}
	
	public static Location getVillagerShopLocation() {
		String w = Main.getInstance().getConfig().getString("ragemode.villagershopspawn.world");
		double x = Main.getInstance().getConfig().getDouble("ragemode.villagershopspawn.x");
		double y = Main.getInstance().getConfig().getDouble("ragemode.villagershopspawn.y");
		double z = Main.getInstance().getConfig().getDouble("ragemode.villagershopspawn.z");
		
		if(w != null) {
			Location loc = new Location(Bukkit.getWorld(w), x, y, z);
			return loc;
		} else {
			return null;
		}
	}
	
	public static Location getRandomMapSpawnLocations() {
		try {
			int spawnnumber = Main.getInstance().getConfig().getInt("ragemode.mapspawn." +  Library.votedmap + ".spawnnumber");
			Random r = new Random();
			int randomzahl = r.nextInt(spawnnumber);
			Location loc = null;
			
			for(int i = 0; i < 40; i++) {
				String w = Main.getInstance().getConfig().getString("ragemode.mapspawn." + Library.votedmap + "." + randomzahl + ".world");
				double x = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + "." + randomzahl + ".x");
				double y = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + "." + randomzahl + ".y");
				double z = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + "." + randomzahl + ".z");
				double yaw = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + "." + randomzahl + ".yaw");
				double pitch = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + "." + randomzahl + ".pitch");
				
				if(w != null) {
					loc = new Location(Bukkit.getWorld(w), x, y, z);
			        loc.setYaw((float)yaw);
			        loc.setPitch((float)pitch);   
					return loc;
				}
			}
			
			return loc;
		} catch (IllegalArgumentException e) {
			System.out.println(Strings.error_no_map);
			return getLobbyLocation();
		}
	}
	
	public static Location getMapSpawnLocation(String wantto, String mapname) {
		Location loc = null;
		
		for(int i = 0; i < 40; i++) {
			String w = Main.getInstance().getConfig().getString("ragemode.mapspawn." + mapname + "." + wantto + ".world");
			double x = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".x");
			double y = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".y");
			double z = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".z");
			double yaw = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".yaw");
			double pitch = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".pitch");
			
			if(w != null) {
				loc = new Location(Bukkit.getWorld(w), x, y, z);
		        loc.setYaw((float)yaw);
		        loc.setPitch((float)pitch);   
				return loc;
			}
		}
		
		return loc;
	}
	
	public static Location getMapMiddleLocation() {
		String w = Main.getInstance().getConfig().getString("ragemode.mapspawn." + Library.votedmap + ".middlepoint.world");
		double x = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + ".middlepoint.x");
		double y = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + ".middlepoint.y");
		double z = Main.getInstance().getConfig().getDouble("ragemode.mapspawn." + Library.votedmap + ".middlepoint.z");
		
		if(w != null) {
			Location loc = new Location(Bukkit.getWorld(w), x, y, z);
			return loc;
		} else {
			return null;
		}
	}
}
