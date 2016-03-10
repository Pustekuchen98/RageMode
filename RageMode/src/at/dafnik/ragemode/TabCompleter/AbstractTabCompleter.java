package at.dafnik.ragemode.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;

public class AbstractTabCompleter implements TabCompleter{

	public String commandString() {
		return null;
	}
	
	public int argumentsInt() {
		return 0;
	}
	
	public List<String> returnList() {
		return null;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {
		if(command.getName().equalsIgnoreCase(commandString()) && arguments.length > argumentsInt()) {
			if(sender instanceof Player) {
				
				List<String> list = (List<String>) returnList();
				if(list == null) {
					List<String> empty = new ArrayList<>();
					list = empty;
				}
				
				return list;
			} else {
				System.out.println(Strings.error_only_player_use);
				return null;
			}
		}
		return null;
	}
}
