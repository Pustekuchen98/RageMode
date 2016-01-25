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
import org.bukkit.inventory.ItemStack;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class AxeEvent implements Listener{
	
	private Main plugin;

	  public AxeEvent(Main main)
	  {
	    this.plugin = main;
	  }
	  
	@EventHandler
	public void Interact(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
			if(player.getItemInHand().getType() == Material.DIAMOND_AXE) {
				if(Main.status == Status.INGAME) {
					Item item = player.getWorld().dropItem(player.getEyeLocation(), player.getItemInHand());
					item.setVelocity(player.getLocation().getDirection().multiply(2D));
					player.setItemInHand(new ItemStack(Material.AIR));
					player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 1000, 1);
					AxeThrow axet = new AxeThrow(player, 0.5, item, plugin);
					axet.start();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
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
