package at.dafnik.ragemode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;

public class Teleport implements CommandExecutor{
	
	private Main plugin;
	
	public Teleport(Main main){
		this.plugin = main;
	}
	
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player){
			Player player = (Player)sender;
			
			//Tpmap
			if (cmd.getName().equalsIgnoreCase("tpmap")){
			 	if(!(plugin.getPower(player) >= 5)){
			 		player.sendMessage(Strings.error_permission);
			 	}else{
			 		if(args.length == 2) {
			 			if(plugin.maps.contains(args[0].toLowerCase())){
			 				String mapname = args[0].toLowerCase();
			 				String chosenumber = args[1];
			 				int spawnnumber = plugin.getConfig().getInt("ragemode.mapspawn." +  mapname + ".spawnnumber");
			 				
			 				if(Integer.valueOf(chosenumber) > 0 && Integer.valueOf(chosenumber) <= spawnnumber) {
				 				String w = plugin.getConfig().getString("ragemode.mapspawn." + mapname + "." + chosenumber + ".world");
								double x = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + chosenumber + ".x");
				 				double y = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + chosenumber + ".y");
				 				double z = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + chosenumber + ".z");
				 				double yaw = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + chosenumber + ".yaw");
				 				double pitch = plugin.getConfig().getDouble("ragemode.mapspawn." + mapname + "." + chosenumber + ".pitch");
							
				 				Location loc = new Location(Bukkit.getWorld(w), x, y, z);
				 				loc.setYaw((float)yaw);
				 				loc.setPitch((float)pitch);
							
				 				player.teleport(loc);
			 				} else {
			 					player.sendMessage(Strings.error_enter_unknown_spawnnumber);
			 				}
			 			} else {
			 				player.sendMessage(Strings.error_unknown_map);
			 			}
			 		} else {
			 			player.sendMessage(Strings.error_enter);
			 		}
			 	}
			 	
			}
			 	 
			if (cmd.getName().equalsIgnoreCase("tplobby")){
				if(!(plugin.getPower(player) >= 5)){
					player.sendMessage(Strings.error_permission);
			 	}else{ 
			 		
			 		String w = plugin.getConfig().getString("ragemode.lobbyspawn.world");
					double x = plugin.getConfig().getDouble("ragemode.lobbyspawn.x");
					double y = plugin.getConfig().getDouble("ragemode.lobbyspawn.y");
					double z = plugin.getConfig().getDouble("ragemode.lobbyspawn.z");
					double yaw = plugin.getConfig().getDouble("ragemode.lobbyspawn.yaw");
					double pitch = plugin.getConfig().getDouble("ragemode.lobbyspawn.pitch");
					
					Location loc = new Location(Bukkit.getWorld(w), x, y, z);
					loc.setYaw((float) yaw);
					loc.setPitch((float) pitch);
					
					player.teleport(loc);
			 	}
			}
		}
		return true;
	}
}
