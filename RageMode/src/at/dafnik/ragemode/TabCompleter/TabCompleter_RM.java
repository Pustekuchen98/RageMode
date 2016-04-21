package at.dafnik.ragemode.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class TabCompleter_RMCommands extends AbstractTabCompleter{

	@Override
	public List<String> returnList() {
		List<String> subcommands = new ArrayList<>();
		
		subcommands.add("setlobby");
		subcommands.add("setmapname");
		subcommands.add("setmapmiddle");
		subcommands.add("setmapspawn");
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
	}
	
	@Override
	public String commandString() {
		return "rm";
	}
	
	@Override
	public int argumentsMaxInt() {
		return 2;
	}
	
	@Override
	public int argumentsMinInt() {
		return 0;
	}
	
	@Override
	public Boolean Status() {
		if(Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) return true;
		else return false;
	}
}
