package at.dafnik.ragemode.API;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import at.dafnik.ragemode.Main.Library;

public class Manager {
	
	//DisplayName Manager
	public static void DisplayNameManagerMethode(Player player, String state) {
		new PlayerListName().set(player, state);
	}

	// Helmet Manager
	public static void HelmetManagerMethode(Player player) {
		ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);;
		LeatherArmorMeta lam;
		lam = (LeatherArmorMeta) item.getItemMeta();
		lam.setDisplayName("§6Helmet");
		
		if (player.hasPermission("ragemode.admin")) lam.setColor(Color.RED);
		else if (player.hasPermission("ragemode.moderator")) lam.setColor(Color.MAROON);	
		else if (player.hasPermission("ragemode.youtuber")) lam.setColor(Color.PURPLE);
		else if (player.hasPermission("ragemode.premium")) lam.setColor(Color.ORANGE);
		else lam.setColor(Color.GREEN);
		
		item.setItemMeta(lam);
		player.getInventory().setHelmet(item);
	}	
}

class PlayerListName implements Runnable {
	private Player player;
	private String state;
	private Thread thread;
	
	public void set(Player player, String state) {
		this.thread = new Thread();
		this.player = player;
		this.state = state;
		
		thread.start();
		run();
	}
	
	@Override
	public void run() {	
		if (player.hasPermission("ragemode.admin")) {
			player.setDisplayName("§4Admin §8| §4" + player.getName());
			if((Library.teams.get(0).getPrefix().length() + player.getName().length()) <= 16) Library.teams.get(0).addEntry(player.getName());
		
		} else if (player.hasPermission("ragemode.moderator")) {
			player.setDisplayName("§cModerator §8| §c" + player.getName());
			if((Library.teams.get(1).getPrefix().length() + player.getName().length()) <= 16) Library.teams.get(1).addEntry(player.getName());
			
		} else if (player.hasPermission("ragemode.youtuber")) {
			player.setDisplayName("§5YouTuber §8| §5" + player.getName());
			if((Library.teams.get(2).getPrefix().length() + player.getName().length()) <= 16) Library.teams.get(2).addEntry(player.getName());
			
		} else if (player.hasPermission("ragemode.premium")) {
			player.setDisplayName("§6" + player.getName());	
			if((Library.teams.get(3).getPrefix().length() + player.getName().length()) <= 16) Library.teams.get(3).addEntry(player.getName());
			
		} else {
			player.setDisplayName("§a" + player.getName());
			if((Library.teams.get(4).getPrefix().length() + player.getName().length()) <= 16) Library.teams.get(4).addEntry(player.getName());
		}
		
		if((Library.teams.get(0).getPrefix().length() + player.getName().length()) <= 16) player.setScoreboard(Library.scoreboard);
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (state == "normal") player.setPlayerListName(player.getDisplayName());
		else if (state == "spectator")player.setPlayerListName("§8[§4X§8]" + player.getDisplayName());
		else System.out.println(Strings.error_not_authenticated_player);
	}
}