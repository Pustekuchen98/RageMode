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
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class PowerUPItemListener implements Listener{
	
	private int time = 30*20;
	
	@EventHandler
	public void DespawnItem(ItemDespawnEvent event) {
		Entity entity = event.getEntity();
		
		if(Main.status == Status.INGAME) {
			if(Library.powerup_entity.contains(entity)) event.setCancelled(true);
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
		
		if(Main.status == Status.INGAME) {
			if(Library.powerup_entity.contains(entity)) {
				event.setCancelled(true);
				
				Holograms holo = Library.powerup_hashmap.get(Integer.valueOf(item.getItemStack().getItemMeta().getDisplayName()));
				for(Player players : Bukkit.getOnlinePlayers()) holo.destroy(players);
				Library.powerup_hashmap.remove(Integer.valueOf(item.getItemStack().getItemMeta().getDisplayName()));
				Library.powerup_list.remove(holo);

				switch(new Random().nextInt(12)) {
				case 0:
					Items.givePlayerDoubleHeart(player);
					if(Main.isMySQL && Main.isShop) if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) Items.givePlayerDoubleHeart(player); 
					player.sendMessage(Strings.powerup_get_0 + Strings.items_doubleheart + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_doubleheart + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 1:
					Items.giverPlayerClaymore(player);
					if(Main.isMySQL && Main.isShop) if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) Items.giverPlayerClaymore(player); 
					player.sendMessage(Strings.powerup_get_0 + Strings.items_claymore + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_claymore + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 2:
					Items.givePlayerMine(player);
					if(Main.isMySQL && Main.isShop) if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) Items.givePlayerMine(player); 
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
					giveSpeed(player);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_speed + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 7:
					player.sendMessage(Strings.powerup_get_0 + Strings.items_invisibility + Strings.powerup_get_1);
					giveInvisibility(player);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_invisibility + Strings.debug_powerup_get_2 + player.getName());
					break;
				
				case 8:
					giveDoubleJump(player);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_doublejump + Strings.debug_powerup_get_2 + player.getName());
					player.sendMessage(Strings.powerup_get_0 + Strings.items_doublejump + Strings.powerup_get_1);
					if(Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_doublejump + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 9:
					Items.givePlayerFlash(player);
					if(Main.isMySQL && Main.isShop) if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) Items.givePlayerFlash(player); 
					player.sendMessage(Strings.powerup_get_0 + Strings.items_flash + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_flash + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 10:
					Items.givePlayerFly(player);
					if(Main.isMySQL && Main.isShop) if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) Items.givePlayerFly(player); 
					player.sendMessage(Strings.powerup_get_0 + Strings.items_fly + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_fly + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 11:
					Items.givePlayerC4(player);
					if(Main.isMySQL && Main.isShop) if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) Items.givePlayerC4(player); 
					player.sendMessage(Strings.powerup_get_0 + Strings.items_c4 + Strings.powerup_get_1);
					Items.givePlayerC4Detonator(player);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + Strings.items_c4 + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				default:
					System.out.println(Strings.error_randommizer_dont_work);
				}

				player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1000, 1);
					
				entity.remove();
				Library.powerup_entity.remove(entity);
			
			} else if(entity.getName() != "PowerUPEntity"){
				event.setCancelled(true);
				entity.remove();
			}
		}
	}
	
	private void giveDoubleJump(Player player) {
		Library.powerup_doublejump.add(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				Library.powerup_doublejump.remove(player);
			}
		}, time);
	}
	
	private void giveInvisibility(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, time, 3, false));
		player.getInventory().setHelmet(null);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				Manager.HelmetManagerMethode(player);
			}
		}, time);
	}
	
	private void giveSpeed(Player player) {
		Library.powerup_speedeffect.add(player);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (time-1), 3));
			}
		}, 20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				Library.powerup_speedeffect.remove(player);
			}
		}, time);
	}
}
