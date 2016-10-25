package at.dafnik.ragemode.Commands;

import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.PowerSystem;
import at.dafnik.ragemode.Main.Main.Status;
import org.bukkit.inventory.ItemStack;

public class Teleport implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player){
			Player player = (Player)sender;
			
			//Tpmap
			if (cmd.getName().equalsIgnoreCase("tpmap")){
			 	if(!(PowerSystem.getPower(player) >= 5)){
			 		player.sendMessage(Strings.error_permission);
			 	}else{
			 		if(args.length == 2) {
			 			if(Library.maps.contains(args[0].toLowerCase())){
			 				String mapname = args[0].toLowerCase();
			 				String chosenumber = args[1];
			 				String wantto = chosenumber;
			 				
			 				Location loc = TeleportAPI.getMapSpawnLocation(wantto, mapname);
			 				if(loc != null) player.teleport(loc);
			 				else player.sendMessage(Strings.error_enter_unknown_spawnnumber);
			 				
			 			} else player.sendMessage(Strings.error_unknown_map);
			 			
			 		} else player.sendMessage(Strings.error_enter);
			 	}
			 	return true;
			}
			 	 
			if (cmd.getName().equalsIgnoreCase("tplobby")){
				if(!(PowerSystem.getPower(player) >= 5)){
					player.sendMessage(Strings.error_permission);
			 	}else{ 
					player.teleport(TeleportAPI.getLobbyLocation());
			 	}
			}

		} else System.out.println(Strings.error_only_player_use);
		return true;
	}
}
