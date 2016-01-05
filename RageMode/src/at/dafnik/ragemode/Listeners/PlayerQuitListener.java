package at.dafnik.ragemode.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Tasks.Ingame;

public class PlayerQuitListener implements Listener{
	
	private Main plugin;
	private Ingame Ingame;

	public PlayerQuitListener(Main main){
		this.plugin = main;
		this.Ingame = new Ingame(plugin);
	}
	
	@EventHandler
	public void Leave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		if(!plugin.spectatorlist.contains(player)) {
			event.setQuitMessage("§3> §r" + player.getDisplayName() + " §3has left the game");
			
			if(Bukkit.getOnlinePlayers().isEmpty()) { 
				plugin.villager.remove();
				plugin.villager = null;
			}
			
			if(Main.status == Status.WARMUP || Main.status == Status.INGAME){		
				//Remove Player from Playerpointslist
				plugin.playerpoints.remove(player);
				
				//Remove from PlayerIngameList
				plugin.ingameplayer.remove(player);
				
				int playerint = plugin.ingameplayer.size();
				int needplayers = plugin.getConfig().getInt("ragemode.settings.neededplayers");
			
				if (playerint <= needplayers -1) {
					Bukkit.broadcastMessage(Strings.error_all_left);
					
					Ingame.win();
				}
			}
		} else if(plugin.spectatorlist.contains(player)) {
			event.setQuitMessage(null);
			
			plugin.spectatorlist.remove(player);
		} else {
			Bukkit.broadcastMessage("§cSomething went terrible wrong");
		}
	}
}
