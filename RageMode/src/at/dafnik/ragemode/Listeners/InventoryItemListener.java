package at.dafnik.ragemode.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class InventoryItemListener implements Listener{
	
	//Pickup
	@EventHandler
	public void ItemPickUp(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		
		if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
				&& player.hasPermission("ragemode.admin")) event.setCancelled(false);
		else if(Main.status != Status.INGAME){
			event.setCancelled(true);
			event.getItem().remove();
		} else event.setCancelled(true);
	}
	
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
			Material item = event.getItem().getType();

			if ((Main.status != Status.PRE_LOBBY && Main.status != Status.LOBBY) || !Library.builder.contains(player)
					|| !player.hasPermission("ragemode.admin")) {
                        if(item == Material.BOW || item == Material.IRON_SPADE || item == Material.DIAMOND_AXE || item == Material.ARROW
                                || item == Material.COMPASS || item == Material.LEATHER_HELMET) {
                            event.setCancelled(true);
                        } else {
                            event.setCancelled(false);
                        }
                    } else {
				event.setCancelled(false);
			}
		}
	}
		
	//Cannot Click in the Inventory
	@EventHandler
	public void InventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			if(event.getCurrentItem() != null) {
				Player player = (Player) event.getWhoClicked();
				Material item = event.getCurrentItem().getType();

				if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
						&& player.hasPermission("ragemode.admin")) {
                    event.setCancelled(false);
                } else {
					if(item == Material.BOW || item == Material.IRON_SPADE || item == Material.DIAMOND_AXE || item == Material.ARROW
							|| item == Material.COMPASS || item == Material.LEATHER_HELMET) {
						event.setCancelled(true);
					} else {
						event.setCancelled(false);
					}
				}
			}
		} else event.setCancelled(true);
	}
	
	@EventHandler
	public void SwapHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		
		if (((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
				&& player.hasPermission("ragemode.admin"))) event.setCancelled(false);
		else event.setCancelled(true);
	}
}
