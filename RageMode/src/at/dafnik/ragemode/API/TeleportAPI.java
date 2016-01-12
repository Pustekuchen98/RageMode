package at.dafnik.ragemode.API;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import at.dafnik.ragemode.Main.Main;

public class TeleportAPI {
	
	private Main plugin;
	
	public TeleportAPI(Main main) {
		this.plugin = main;
	}
	
	public Location getLobbyLocation() {
		String w = plugin.getConfig().getString("ragemode.lobbyspawn.world");
		double x = plugin.getConfig().getDouble("ragemode.lobbyspawn.x");
		double y = plugin.getConfig().getDouble("ragemode.lobbyspawn.y");
		double z = plugin.getConfig().getDouble("ragemode.lobbyspawn.z");
		double yaw = plugin.getConfig().getDouble("ragemode.lobbyspawn.yaw");
		double pitch = plugin.getConfig().getDouble("ragemode.lobbyspawn.pitch");
		
		if(w != null) {
			Location loc = new Location(Bukkit.getWorld(w), x, y, z);
			loc.setYaw((float) yaw);
			loc.setPitch((float) pitch);
			return loc;
		} else {
			return null;
		}
	}
	
	public Location getVillagerShopLocation() {
		String w = plugin.getConfig().getString("ragemode.villagershopspawn.world");
		double x = plugin.getConfig().getDouble("ragemode.villagershopspawn.x");
		double y = plugin.getConfig().getDouble("ragemode.villagershopspawn.y");
		double z = plugin.getConfig().getDouble("ragemode.villagershopspawn.z");
		double yaw = plugin.getConfig().getDouble("ragemode.villagershopspawn.yaw");
		double pitch = plugin.getConfig().getDouble("ragemode.villagershopspawn.pitch");
		
		if(w != null) {
			Location loc = new Location(Bukkit.getWorld(w), x, y, z);
			loc.setYaw((float) yaw);
			loc.setPitch((float) pitch);
			return loc;
		} else {
			return null;
		}
	}
	
	public Location getRandomMapSpawnLocations() {
		int spawnnumber = plugin.getConfig().getInt("ragemode.mapspawn." +  plugin.votedmap + ".spawnnumber");
		
		Random r = new Random();
		int randomzahl = r.nextInt(spawnnumber);
		
		Location loc;
		
		for(int i = 0; i < 40; i++) {
		
			String w = plugin.getConfig().getString("ragemode.mapspawn." + plugin.votedmap + "." + randomzahl + ".world");
			double x = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + "." + randomzahl + ".x");
			double y = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + "." + randomzahl + ".y");
			double z = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + "." + randomzahl + ".z");
			double yaw = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + "." + randomzahl + ".yaw");
			double pitch = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + "." + randomzahl + ".pitch");
			
			if(w != null) {
				loc = new Location(Bukkit.getWorld(w), x, y, z);
		        loc.setYaw((float)yaw);
		        loc.setPitch((float)pitch);   
				return loc;
			}
		}
		
		return loc = null;
	}
	
	public Location getMapSpawnLocation(int wantto, String mapname) {
		int spawnnumber = plugin.getConfig().getInt("ragemode.mapspawn." +  plugin.votedmap + ".spawnnumber");
		Location loc;
		
		if(wantto > spawnnumber) return null;
		
		for(int i = 0; i < 40; i++) {
		
			String w = plugin.getConfig().getString("ragemode.mapspawn." + mapname + "." + wantto + ".world");
			double x = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".x");
			double y = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".y");
			double z = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".z");
			double yaw = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".yaw");
			double pitch = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + wantto + ".pitch");
			
			if(w != null) {
				loc = new Location(Bukkit.getWorld(w), x, y, z);
		        loc.setYaw((float)yaw);
		        loc.setPitch((float)pitch);   
				return loc;
			}
		}
		
		return null;
	}
}
