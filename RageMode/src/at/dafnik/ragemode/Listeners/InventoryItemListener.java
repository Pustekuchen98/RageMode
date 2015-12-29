package at.dafnik.ragemode.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class InventoryItemListener implements Listener{
	
	Main plugin;
	
	public InventoryItemListener(Main main) {
		this.plugin = main;
	}
	
	//Dropping is off
	@EventHandler
	public void DropItem(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		if (player.hasPermission("ragemode.admin")) {
			if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) event.setCancelled(false);
			else event.setCancelled(true);
		} else {
			event.setCancelled(true);
		}
	}
	
	//Cannot change Inventory
	@EventHandler
	public void IventoryMove(InventoryMoveItemEvent event) {
		Player player = (Player) event.getSource().getHolder();
			if (player.hasPermission("ragemode.admin")) {
				if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) event.setCancelled(false);
				else event.setCancelled(true);
			} else {
				event.setCancelled(true);
			}
	}
		
	//Cannot Click in the Inventory
	@EventHandler
	public void InventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (player.hasPermission("ragemode.admin")) {
			if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) event.setCancelled(false);
			else event.setCancelled(true);
		} else {
			event.setCancelled(true);
		}
	}
}
