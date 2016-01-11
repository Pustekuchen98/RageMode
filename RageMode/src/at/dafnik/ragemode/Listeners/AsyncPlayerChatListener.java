package at.dafnik.ragemode.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class AsyncPlayerChatListener implements Listener{
	
	private Main plugin;
	
	public AsyncPlayerChatListener(Main main){
		this.plugin = main;
	}
	
	@EventHandler
    public void onChat( AsyncPlayerChatEvent event ) {
		Player player = event.getPlayer();
		
		if(!plugin.spectatorlist.contains(player)) {
			event.setFormat(player.getDisplayName() + "§8: §f" + event.getMessage());
	
		} else if(plugin.spectatorlist.contains(player)){
			if(Main.status == Status.INGAME) {
				event.setCancelled(true);
				for(Player spectator : plugin.spectatorlist) {
					spectator.sendMessage("§8[§4X§8]" + player.getDisplayName() + "§8: §f" + event.getMessage());
				}
			} else {
				event.setFormat("§8[§4X§8]" + player.getDisplayName() + "§8: §f" + event.getMessage());
			}
		} else {
			System.out.println(Strings.error_not_authenticated_player);
		}
    }
}
