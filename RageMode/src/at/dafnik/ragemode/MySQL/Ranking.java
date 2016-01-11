package at.dafnik.ragemode.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;

public class Ranking {
	
	private Main plugin;
	private Boolean happened = false;
	
	public Ranking(Main main) {
		this.plugin = main;
	}
	
	static HashMap<Integer, String> rang = new HashMap<Integer, String>();
	
	public void set() {
		
		if(Main.isMySQL) {
			
			int howmany = plugin.getConfig().getInt("ragemode.ranking.rankingnumber");
			int rotation = plugin.getConfig().getInt("ragemode.ranking.rotation");
			
			ResultSet rs = Main.mysql.query("SELECT UUID FROM Stats ORDER BY POINTS DESC LIMIT " + howmany);
			
			int in = 0;
			
			try {
				while(rs.next()) {
					in++;
					rang.put(in, rs.getString("UUID"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			List<Location> LOC = new ArrayList<Location>();
			
			for(int ix = 0; ix < howmany; ix++) {
				String w = plugin.getConfig().getString("ragemode.ranking.positions." + ix + ".world");
				double x = plugin.getConfig().getInt("ragemode.ranking.positions." + ix + ".x");
				double y = plugin.getConfig().getInt("ragemode.ranking.positions." + ix + ".y");
				double z = plugin.getConfig().getInt("ragemode.ranking.positions." + ix + ".z");
				
				Location lloc = new Location(Bukkit.getWorld(w), x, y, z);
				
				LOC.add(lloc);
			}
			
			for(int i = 0; i < LOC.size(); i++){
				int id = i+1;
				
				LOC.get(i).getBlock().setType(Material.SKULL);
				Skull s = (Skull) LOC.get(i).getBlock().getState();
				s.setSkullType(SkullType.PLAYER);
				
				if(rotation == 0) {
					s.setRotation(BlockFace.NORTH);
				} else if(rotation == 1) {
					s.setRotation(BlockFace.EAST);
				} else if(rotation == 2) {
					s.setRotation(BlockFace.SOUTH);
				} else if(rotation == 3) {
					s.setRotation(BlockFace.WEST);
				} else {
					System.out.println(Strings.error_rankingwall_headrotation);
				}
				
				String name = null;
				
				if(id <= in) {
					name = Bukkit.getOfflinePlayer(UUID.fromString(rang.get(id))).getName();

					s.setOwner(name);
					s.update();
					
					Location newloc = new Location(LOC.get(i).getWorld(), LOC.get(i).getX(), LOC.get(i).getY()-1, LOC.get(i).getZ());
					
					if(newloc.getBlock().getState() instanceof Sign) {
						BlockState b = newloc.getBlock().getState();
						Sign S = (Sign) b;
						
						S.setLine(0, "Rank #" + id);
						S.setLine(1, name);
						S.setLine(2, SQLStats.getPoints(rang.get(id)) + " Points");
						S.setLine(3, SQLStats.getWonGames(rang.get(id)) + " Wins");
						S.update();
					}
				} else {
					if(!happened) {
						System.out.println(Strings.error_more_rankingwall_places_then_player_in_mysql);
						happened = true;		
					}
				}
			}
		}
	}
}
