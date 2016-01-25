package at.dafnik.ragemode.Holograms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;

public class HologramsScheduler implements Runnable{
	
	final Player player;
	private Main plugin;
	
	public HologramsScheduler(Main main, Player player){
		this.plugin = main;
		this.player = player;
		Bukkit.getScheduler().runTaskLater(plugin, this, 20*2);
	}
	
	@Override
	public void run() {	
		//440, 62, 444
		
		String w = plugin.getConfig().getString("ragemode.hologram.world");
		double x = plugin.getConfig().getDouble("ragemode.hologram.x");
		double y = plugin.getConfig().getDouble("ragemode.hologram.y");
		double z = plugin.getConfig().getDouble("ragemode.hologram.z");
		
		Location loc = new Location(Bukkit.getWorld(w), x, y, z);
		List<String> lines = new ArrayList<String>();
		lines.add("§bRageMode §6Stats");
		
		int kills = SQLStats.getKills(player.getUniqueId().toString());
		int deaths = SQLStats.getDeaths(player.getUniqueId().toString());
		int playedgames = SQLStats.getPlayedGames(player.getUniqueId().toString());
		int wongames = SQLStats.getWonGames(player.getUniqueId().toString());
		int points = SQLStats.getPoints(player.getUniqueId().toString());
		int coins = SQLCoins.getCoins(player.getUniqueId().toString());
		int bowkills = SQLStats.getBowKills(player.getUniqueId().toString());
		int knifekills = SQLStats.getKnifeKills(player.getUniqueId().toString());
		int axtkills = SQLStats.getAxtKills(player.getUniqueId().toString());
		int resets = SQLStats.getResets(player.getUniqueId().toString());
		int suicides = SQLStats.getSuicides(player.getUniqueId().toString());
		
		float KD;
		try {
			KD = ((float) kills) / ((float) deaths);
		} catch (ArithmeticException ex) {
			KD = kills;
		}
		float rund = (float)(((int)(KD*100))/100.0);
		
		lines.add("§3Points§8: §e" + points);
		lines.add("§3Coins§8: §e" + coins);
		lines.add("§3Kills§8: §e" + kills);
		lines.add("§3Bow Kills§8: §e" + bowkills);
		lines.add("§3Knife Kills§8: §e" + knifekills);
		lines.add("§3Axt Kills§8: §e" + axtkills);
		lines.add("§3Suicides§8: §e" + suicides);
		lines.add("§3Deaths§8: §e" + deaths);
		lines.add("§3Kills/Deaths§8: §e" + rund);
		lines.add("§3Played games§8: §e" + playedgames);
		lines.add("§3Won games§8: §e" + wongames);
		lines.add("§3Stats resets§8: §e" + resets);
		
		Holograms holo = new Holograms(loc, lines);
		holo.display(player);	
	}
}
