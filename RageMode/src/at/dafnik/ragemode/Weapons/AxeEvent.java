package at.dafnik.ragemode.Weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Threads.AxeThrowThread;

public class AxeEvent implements Listener{
	  
	@EventHandler
	public void Interact(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
			if(Main.status == Status.INGAME) {
				if(player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE) {
				
					Item item = player.getWorld().dropItem(player.getEyeLocation(), player.getInventory().getItemInMainHand());							
					player.getInventory().remove(Material.DIAMOND_AXE);				
					item.setVelocity(player.getLocation().getDirection().multiply(2D));
					player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1000, 1);
					AxeThrowThread axet = new AxeThrowThread(player, 0.5, item);
					axet.start();
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						@Override
						public void run() {
							item.remove();
							axet.stop();
						}
					}, 15*20);
				}
			}
		}
	}	
}
