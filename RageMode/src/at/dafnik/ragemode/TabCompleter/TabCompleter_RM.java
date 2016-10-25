package at.dafnik.ragemode.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;

public class TabCompleter_RM implements TabCompleter {

	List<String> subcommands;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {
		if (sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("rm")) {
				switch(arguments.length) {
				case 1:
					subcommands = null;
					subcommands = new ArrayList<>();
					
					subcommands.add("setlobby");
					subcommands.add("setmapname");
					subcommands.add("setmapmiddle");
					subcommands.add("setmapspawn");
					subcommands.add("setmapauthor");
					subcommands.add("setpowerupspawn");
					subcommands.add("spawnpowerup");
					subcommands.add("setneededplayers");
					subcommands.add("setgametime");
					subcommands.add("sethologram");
					subcommands.add("setvillagershop");
					subcommands.add("builder");
					subcommands.add("setranking");
					subcommands.add("mysql");
					subcommands.add("bungee");

					return subcommands;
					
				case 2:
					String eingabe = arguments[0];
					
					if(eingabe.equalsIgnoreCase("SETMAPAUTHOR")
							|| eingabe.equals("SETMAPMIDDLE")
							|| eingabe.equals("SETMAPSPAWN")
							|| eingabe.equals("SETPOWERUPSPAWN")) {
						return Library.maps;
						
					} else if(eingabe.equalsIgnoreCase("MYSQL")) {
						subcommands = null;
						subcommands = new ArrayList<>();
						
						subcommands.add("sethost");
						subcommands.add("setdatabase");
						subcommands.add("setusername");
						subcommands.add("setpassword");
						
						return subcommands;
						
					} else if(eingabe.equalsIgnoreCase("bungee")) {
						subcommands = null;
						subcommands = new ArrayList<>();
						
						subcommands.add("setlobbyserver");
						
						return subcommands;
					} else 
						return null;
					
				default:
					return null;
					
				}		
			}
		} else {
			System.out.println(Strings.error_only_player_use);
			return null;
		}
		
		return null;
	}
}
