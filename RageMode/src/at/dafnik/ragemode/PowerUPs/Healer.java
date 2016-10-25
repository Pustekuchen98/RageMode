package at.dafnik.ragemode.PowerUPs;

import at.dafnik.ragemode.Main.Library;
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

import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Healer implements Listener{
	
	private int time = 15*20;
	
	@EventHandler
	public void Interact(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(Main.status == Status.INGAME) {
				if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.REDSTONE) {
					Player player = event.getPlayer();

					if(Library.powerup_shield.contains(player)) {
						player.setMaxHealth(80);
						player.setHealth(80);
					} else {
						player.setMaxHealth(40);
						player.setHealth(40);
					}
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 3));
					int amount = event.getItem().getAmount() - 1;
					player.getInventory().remove(event.getItem());
					
					if(amount > 0) {
						for(int i = 0; i < amount; i++) {
							Items.givePlayerDoubleHeart(player);
						}
					}
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1000.0F, 1.0F);
						
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
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
