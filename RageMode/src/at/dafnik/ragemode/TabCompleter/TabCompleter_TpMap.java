package at.dafnik.ragemode.TabCompleter;

import java.util.List;

import at.dafnik.ragemode.Main.Library;

public class TabCompleter_TpMap extends AbstractTabCompleter{
	
	@Override
	public List<String> returnList() {
		return Library.maps;
	}
	
	@Override
	public String commandString() {
		return "tpmap";
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
