package at.dafnik.ragemode.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
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
			 	if(!(Main.getPower(player) >= 5)){
			 		player.sendMessage(Strings.error_permission);
			 	}else{
			 		if(args.length == 2) {
			 			if(plugin.maps.contains(args[0].toLowerCase())){
			 				String mapname = args[0].toLowerCase();
			 				String chosenumber = args[1];
			 				String wantto = chosenumber;
			 				
			 				Location loc = new TeleportAPI(plugin).getMapSpawnLocation(wantto, mapname);
			 				if(loc != null) player.teleport(loc);
			 				else player.sendMessage(Strings.error_enter_unknown_spawnnumber);
			 				
			 			} else player.sendMessage(Strings.error_unknown_map);
			 			
			 		} else player.sendMessage(Strings.error_enter);
			 	}
			 	return true;
			}
			 	 
			if (cmd.getName().equalsIgnoreCase("tplobby")){
				if(!(Main.getPower(player) >= 5)){
					player.sendMessage(Strings.error_permission);
			 	}else{ 
					player.teleport(new TeleportAPI(plugin).getLobbyLocation());
			 	}
			}
		} else System.out.println(Strings.error_only_player_use);
		return true;
	}
}
