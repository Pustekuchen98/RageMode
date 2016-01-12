package at.dafnik.ragemode.PowerUPs;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Manager;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class PowerUPItemListener implements Listener{
	
	private Main plugin;
	private int time = 30*20;
	
	public PowerUPItemListener(Main main){
		this.plugin = main;
	}
	
	@EventHandler
	public void DespawnItem(ItemDespawnEvent event) {
		Entity entity = event.getEntity();
		
		if(Main.status == Status.INGAME) {
			if(plugin.powerupentity.contains(entity)) event.setCancelled(true);
			else event.setCancelled(false);
		} else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler
	public void GetItem(PlayerPickupItemEvent event){
		Player player = event.getPlayer();
		Entity entity = event.getItem();
		Item item = event.getItem();
		
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(player.hasPermission("ragemode.admin")) event.setCancelled(false);
			else event.setCancelled(true);
		} else {
			if(plugin.powerupentity.contains(entity)) {
				event.setCancelled(true);
				
				Holograms holo = plugin.poweruphashmap.get(Integer.valueOf(item.getItemStack().getItemMeta().getDisplayName()));
				for(Player players : Bukkit.getOnlinePlayers()) holo.destroy(players);

				switch(new Random().nextInt(8)) {
				case 0:
					Items.givePlayerDoubleHeart(player);
					player.sendMessage(Strings.powerup_get_0 + Strings.items_doubleheart + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_doubleheart + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 1:
					Items.giverPlayerClaymore(player);
					player.sendMessage(Strings.powerup_get_0 + Strings.items_claymore + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_claymore + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 2:
					Items.givePlayerMine(player);
					player.sendMessage(Strings.powerup_get_0 + Strings.items_mine + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_mine + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 3:
					player.sendMessage(Strings.powerup_get_0 + Strings.items_jump + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, time, 4));
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_jump + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 4:
					player.sendMessage(Strings.powerup_get_0 + Strings.items_slowness + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 2));
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_slowness + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 5:
					player.sendMessage(Strings.powerup_get_0 + Strings.items_blindness + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 1));
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_blindness + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 6:
					player.sendMessage(Strings.powerup_get_0 + Strings.items_speed + Strings.powerup_get_1);
					plugin.powerupspeedeffect.add(player);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (time-1), 3));
						}
					}, 20);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.powerupspeedeffect.remove(player);
						}
					}, time);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_blindness + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 7:
					player.sendMessage(Strings.powerup_get_0 + Strings.items_invisibility + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, time, 3, false));
					player.getInventory().setHelmet(null);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							Manager.HelmetManagerMethode(player);
						}
					}, time);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_invisibility + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				default:
					System.out.println(Strings.error_randommizer_dont_work);
				}

				player.playSound(player.getLocation(), Sound.LEVEL_UP, 1000, 1);
					
				entity.remove();
				plugin.powerupentity.remove(entity);
				plugin.poweruphashmap.remove(Integer.valueOf(item.getItemStack().getItemMeta().getDisplayName()));
				
			} else {
				event.setCancelled(true);
				entity.remove();
			}
		}
	}
}
