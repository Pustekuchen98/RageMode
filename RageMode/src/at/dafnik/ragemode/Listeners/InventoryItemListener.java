package at.dafnik.ragemode.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class InventoryItemListener implements Listener{
	
	//Dropping is off
	@EventHandler
	public void DropItem(PlayerDropItemEvent event){
		Player player = event.getPlayer();

		if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
				&& player.hasPermission("ragemode.admin")) event.setCancelled(false);
		else event.setCancelled(true);	
	}
	
	//Cannot change Inventory
	@EventHandler
	public void IventoryMove(InventoryMoveItemEvent event) {
		if(event.getSource().getHolder() instanceof Player) {
			Player player = (Player) event.getSource().getHolder();
			if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
					&& player.hasPermission("ragemode.admin")) event.setCancelled(false);
			else event.setCancelled(true);	
		}
	}
		
	//Cannot Click in the Inventory
	@EventHandler
	public void InventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			
			if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
					&& player.hasPermission("ragemode.admin")) event.setCancelled(false);
			else event.setCancelled(true);	
		
		} else event.setCancelled(true);
	}
}
