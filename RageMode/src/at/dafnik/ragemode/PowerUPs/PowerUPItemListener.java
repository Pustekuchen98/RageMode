package at.dafnik.ragemode.PowerUPs;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class PowerUPItemListener implements Listener{
	
	private Main plugin;
	
	private String doubleheart = "§4Double Heart";
	private String claymore = "§dClaymore";
	private String mine = "§5Mine";
	private String jump = "§aJump boost";
	private String slowness = "§1Slowness";
	private String blindness = "§1Blindness";
	private String speed = "§aSpeed";
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

				switch(new Random().nextInt(7)) {
				case 0:
					ItemStack i = new ItemStack(Material.REDSTONE, 1);
					ItemMeta imd = i.getItemMeta();
					imd.setDisplayName(doubleheart);
					i.setItemMeta(imd);
					player.getInventory().setItem(5, i);
					player.sendMessage(Strings.powerup_get_0 + doubleheart + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + doubleheart + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 1:
					ItemStack i2 = new ItemStack(Material.FLOWER_POT_ITEM, 4);
					ItemMeta imd2 = i2.getItemMeta();
					imd2.setDisplayName(claymore);
					i2.setItemMeta(imd2);
					player.getInventory().setItem(7, i2);
					player.sendMessage(Strings.powerup_get_0 + claymore + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + claymore + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 2:
					ItemStack i3 = new ItemStack(Material.STONE_PLATE, 2);
					ItemMeta imd3 = i3.getItemMeta();
					imd3.setDisplayName(mine);
					i3.setItemMeta(imd3);
					player.getInventory().setItem(6, i3);
					player.sendMessage(Strings.powerup_get_0 + mine + Strings.powerup_get_1);
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + mine + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 3:
					player.sendMessage(Strings.powerup_get_0 + jump + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, time, 4));
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + jump + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 4:
					player.sendMessage(Strings.powerup_get_0 + slowness + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 2));
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + slowness + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 5:
					player.sendMessage(Strings.powerup_get_0 + blindness + Strings.powerup_get_1);
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 1));
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + blindness + Strings.debug_powerup_get_2 + player.getName());
					break;
					
				case 6:
					player.sendMessage(Strings.powerup_get_0 + speed + Strings.powerup_get_1);
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
					if (Main.isDebug) System.out.println(Strings.debug_powerup_get_1 + blindness + Strings.debug_powerup_get_2 + player.getName());
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
