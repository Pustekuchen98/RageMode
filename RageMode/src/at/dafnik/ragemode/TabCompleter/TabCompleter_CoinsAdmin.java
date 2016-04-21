package at.dafnik.ragemode.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter_CoinsAdmin extends AbstractTabCompleter{
	
	@Override
	public List<String> returnList() {
		List<String> subcommands = new ArrayList<>();
		
		subcommands.add("add");
		subcommands.add("remove");
		
		return subcommands;
	}
	
	@Override
	public String commandString() {
		return "coinsadmin";
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
		return true;
	}
}
