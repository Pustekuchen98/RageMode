package at.dafnik.ragemode.API;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import at.dafnik.ragemode.Main.Main;

public class Manager {
	
	private Main plugin;
	
	public Manager(Main main) {
		this.plugin = main;
	}
	
	//DisplayName Manager
	public void DisplayNameManagerMethode(Player player) {
		if (player.hasPermission("ragemode.admin")) player.setDisplayName("§4Admin §8| §4" + player.getName());
		else if (player.hasPermission("ragemode.moderator")) player.setDisplayName("§cModerator §8| §c" + player.getName());
		else if (player.hasPermission("ragemode.youtuber")) player.setDisplayName("§5YouTuber §8| §5" + player.getName());
		else if (player.hasPermission("ragemode.premium")) player.setDisplayName("§6" + player.getName());		
		else player.setDisplayName("§a" + player.getName());
			
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(!plugin.spectatorlist.contains(player)) player.setPlayerListName(player.getDisplayName());
				else if(plugin.spectatorlist.contains(player)) player.setPlayerListName("§8[§4X§8]" + player.getDisplayName());	
				else System.out.println("[RageMode] ERROR: Not authenticated player is on the server! Manager don't work");	
			}
		}, 5);	
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