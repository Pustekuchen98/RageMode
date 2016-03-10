package at.dafnik.ragemode.TabCompleter;

import java.util.List;

import at.dafnik.ragemode.Main.Library;

public class VoteTabCompleter extends AbstractTabCompleter{

	@Override
	public List<String> returnList() {
		return Library.mapstovote;
	}
	
	@Override
	public String commandString() {
		return "vote";
	}
	
	@Override
	public int argumentsInt() {
		return 0;
	}

}
