package at.dafnik.ragemode.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Tasks.Ingame;

public class PlayerQuitListener implements Listener{
	
	private Ingame Ingame;

	public PlayerQuitListener(){
		this.Ingame = new Ingame();
	}
	
	@EventHandler
	public void Leave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		if(!Library.spectatorlist.contains(player)) {
			event.setQuitMessage("§7« §r" + player.getDisplayName() + " §7left the game");
			
			if(Bukkit.getOnlinePlayers().size() == 1 && Library.villager != null) { 
				Library.villager.stop();
				Library.villager = null;
			}
			
			if(Main.status == Status.WARMUP || Main.status == Status.INGAME){		
				//Remove Player from Playerpointslist
				Library.playerpoints.remove(player);
				
				//Remove from PlayerIngameList
				Library.ingameplayer.remove(player);
				
				int playerint = Library.ingameplayer.size();
			
				if (playerint < 2) {
					Bukkit.broadcastMessage(Strings.error_all_left);
					
					Ingame.win();
				}
			}
		} else if(Library.spectatorlist.contains(player)) {
			event.setQuitMessage(null);
			
			Library.spectatorlist.remove(player);
		} else {
			Bukkit.broadcastMessage(Strings.error_not_authenticated_player);
		}
	}
}
