package at.dafnik.ragemode.Weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class Knife implements Listener{
	
	List<Integer> idlists = new ArrayList<>();
	List<Player> knifelist = new ArrayList<>();
	
	HashMap<Player, Boolean> allowed = new HashMap<Player, Boolean>();
	HashMap<Player, Integer> time = new HashMap<Player, Integer>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getInventory().getItemInMainHand().getType() == Material.IRON_SPADE) {
				if(Main.status == Status.INGAME) {
					if(allowed.get(player) == null) allowed.put(player, true);
					
					if(allowed.get(player) == true) {							
						allowed.put(player, false);
						
						double radius = 6;
						double vectormulti = 2;
						double vectory = 1;
						
						if(Main.isMySQL && Main.isShop) {
							if(SQLCoins.getKnockbackUpgrade(player.getUniqueId().toString())) {
								radius = 12;
								vectormulti = 4;
								vectory = 0.8;
							}
						}
						
						for(Entity entities : player.getNearbyEntities(radius, radius, radius)) {
							if(entities instanceof Player || entities instanceof Arrow || entities instanceof Egg) {						
								
								int aX = entities.getLocation().getBlockX();
								int aY = entities.getLocation().getBlockY();
								int aZ = entities.getLocation().getBlockZ();
								
								int bX = player.getLocation().getBlockX();
								int bY = player.getLocation().getBlockY();
								int bZ = player.getLocation().getBlockZ();
								
								int X = aX - bX;
								int Y = aY - bY;
								int Z = aZ - bZ;
								
								Vector vector = new Vector(X, Y, Z).normalize();
								vector.multiply(vectormulti);
								vector.setY(vectory);
								
								if(entities instanceof Player){
									if(Library.ingameplayer.contains((Player)entities) && !(Library.respawnsafe.contains((Player)entities))){
										entities.setVelocity(vector);
										((Player) entities).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 3));
										((Player) entities).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 3*20, 3));
										((Player) entities).damage(2, player);
									}
								} else entities.setVelocity(vector);
							}									
						}
						
						if(time.get(player) == null || time.get(player) == 0) time.put(player, 10);
						
						idlists.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
							@Override
							public void run(){
								if(time.get(player) <=0){
									give(player, time.get(player), item);
									allowed.replace(player, true);
									Bukkit.getScheduler().cancelTask(idlists.get(0));
									idlists.remove(0);
									return;
								}
								
								if(time.get(player) == 10) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 9) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 8) {
									give(player, time.get(player), item);	
								} else if(time.get(player) == 7) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 6) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 5) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 4) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 3) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 2) {
									give(player, time.get(player), item);
								} else if(time.get(player) == 1) {
									give(player, time.get(player), item);
								}
								
								time.put(player, time.get(player) - 1);
							}
						}, 0L, 20L));
					}
				}
			}
		}
	}
	
	private void give(Player player, int howmany, ItemStack item) {
		if(!Library.respawnsafe.contains(player)) {	
			if(howmany == 10) item.setDurability((short)250);
			ItemMeta imd = item.getItemMeta();
			if(!(howmany == 0)) {
				imd.setDisplayName("§cKnife §8[§6" + howmany + "§8]");
				item.setDurability((short) (item.getDurability() - 25));
			} else {
				imd.setDisplayName("§cKnife §8[§6Ready§8]");
				item.setDurability((short)0);
			}
			item.setItemMeta(imd);
			
		}
	}
	
	@EventHandler
	public void EntityDamage(EntityDamageByEntityEvent event) {
		if(Main.status == Status.INGAME) {
			if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) { 
				Player killer = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				
				if (!Library.respawnsafe.contains(victim)) {
					if (killer.getInventory().getItemInMainHand().getType() == Material.IRON_SPADE) {
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							@Override
							public void run() {
								if (knifelist.contains(killer)) {
									knifelist.remove(killer);

									victim.removeMetadata("killedWith", Main.getInstance());
									victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "knife"));
									event.setCancelled(false);

									double q = victim.getLocation().getDirection().dot(killer.getLocation().getDirection());
									if (q > 0) {
										victim.setHealth(0);
										if (killer.getHealth() + 6 > 20)
											killer.setHealth(20);
										else
											killer.setHealth(killer.getHealth() + 6);

										killer.sendMessage(Strings.kill_backstab_knife);

									} else victim.damage(14, killer);
								}
							}
						}, 1);

					} else event.setCancelled(true);

				} else event.setCancelled(true);
				
			} else event.setCancelled(true);
			
		} else event.setCancelled(true);
	}
	
	@EventHandler
	public void onHit(PlayerItemDamageEvent event) {
		if(event.getItem().getType() == Material.IRON_SPADE && Main.status == Status.INGAME) knifelist.add(event.getPlayer()); //Killer will be addet to the List
	}
}