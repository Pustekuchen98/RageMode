package at.dafnik.ragemode.PowerUPs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Healer implements Listener{
	
	private Main plugin;
	
	public Healer(Main main) {
		this.plugin = main;
	}
	
	private int time = 15*20;
	
	@EventHandler
	public void Interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {			
			if(player.getItemInHand().getType() == Material.REDSTONE) {
				if(Main.status == Status.INGAME) {
					player.setMaxHealth(40);
					player.setHealth(40);
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 3));
					player.getInventory().remove(Material.REDSTONE);
					player.getWorld().playSound(player.getLocation(), Sound.EAT, 1000.0F, 1.0F);
						
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
								
							player.setMaxHealth(20);
							player.removePotionEffect(PotionEffectType.REGENERATION);
								
						}
					}, time);
				}
			}
		}
	}
}
