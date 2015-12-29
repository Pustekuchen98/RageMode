package at.dafnik.ragemode.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class BlockBedListener implements Listener{
	
	private Main plugin;
	
	public BlockBedListener(Main main) {
		this.plugin = main;
	}
	
	//Cannot Lay in Bed
	@EventHandler
	private void PlayerBedEnter(PlayerBedEnterEvent event){
		event.setCancelled(true);	
	}
	
	@EventHandler
	public void BlockBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}
		
	//Cannot break Blocks
	@EventHandler
	private void BlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if (player.hasPermission("ragemode.admin")) {
			if((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && plugin.builder.get(player) != null) {
				if(plugin.builder.get(player)) event.setCancelled(false);
				else event.setCancelled(true);
			} else {
				event.setCancelled(true);
			}
		} else {
			event.setCancelled(true);
		}
	}
}
