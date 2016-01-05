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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Knife implements Listener{
	
	private Main plugin;
	
	public Knife(Main main) {
		this.plugin = main;
	}
	
List<Integer> idlists = new ArrayList<>();
	
	HashMap<Player, Boolean> allowed = new HashMap<Player, Boolean>();
	HashMap<Player, Integer> time = new HashMap<Player, Integer>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand().getType() == Material.IRON_SPADE) {
				if(Main.status == Status.INGAME) {
					if(allowed.get(player) == null) allowed.put(player, true);
					
					if(allowed.get(player) == true) {							
						allowed.put(player, false);
						
						for(Entity entities : player.getNearbyEntities(6, 6, 6)) {
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
								vector.multiply(2.0D);
								vector.setY(0.8D);
								
								if(entities instanceof Player){
									if(plugin.ingameplayer.contains((Player)entities) && !(plugin.respawnsafe.contains((Player)entities))){
										entities.setVelocity(vector);
										((Player) entities).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 3));
										((Player) entities).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 3*20, 3));
										((Player) entities).damage(2);
									}
								} else entities.setVelocity(vector);
							}									
						}
						
						if(time.get(player) == null || time.get(player) == 0) time.put(player, 10);
						
						idlists.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
							@Override
							public void run(){
								if(time.get(player) <=0){
									give(player, time.get(player));
									allowed.replace(player, true);
									Bukkit.getScheduler().cancelTask(idlists.get(0));
									idlists.remove(0);
									return;
								}
								
								if(time.get(player) == 10) {
									give(player, time.get(player));
								} else if(time.get(player) == 9) {
									give(player, time.get(player));
								} else if(time.get(player) == 8) {
									give(player, time.get(player));	
								} else if(time.get(player) == 7) {
									give(player, time.get(player));
								} else if(time.get(player) == 6) {
									give(player, time.get(player));
								} else if(time.get(player) == 5) {
									give(player, time.get(player));
								} else if(time.get(player) == 4) {
									give(player, time.get(player));
								} else if(time.get(player) == 3) {
									give(player, time.get(player));
								} else if(time.get(player) == 2) {
									give(player, time.get(player));
								} else if(time.get(player) == 1) {
									give(player, time.get(player));
								}
								
								time.put(player, time.get(player) - 1);
							}
						}, 0L, 20L));
					}
				}
			}
		}
	}
	
	private void give(Player player, int howmany) {
		if(!plugin.respawnsafe.contains(player)) {
			ItemStack i = null;
			if(!(howmany == 0)) i = new ItemStack(Material.IRON_SPADE, howmany);
			else i = new ItemStack(Material.IRON_SPADE, 1);
			ItemMeta imd = i.getItemMeta();
			if(!(howmany == 0)) imd.setDisplayName("§cKnife §8[§6" + howmany + "§8]");
			else imd.setDisplayName("§cKnife §8[§6Ready§8]");
			i.setItemMeta(imd);
			player.getInventory().setItem(1, i);
		}
	}
	
	@EventHandler
	public void onIn(EntityDamageByEntityEvent event) {
		// Messer Damage
		if(Main.status == Status.INGAME) {
			if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) { 
				Player killer = (Player) event.getDamager();
				Player victim = (Player) event.getEntity();
				
				if (killer.getItemInHand() != null && killer.getItemInHand().getType() == Material.IRON_SPADE) {
					if(!plugin.respawnsafe.contains(victim)) {
						if(victim.getLocation().distance(killer.getLocation()) <=3) {
							if(victim.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
								plugin.killGroundremover(victim);
								plugin.playerknifelist.add(victim);
								event.setCancelled(false);
								
								double q = victim.getLocation().getDirection().dot(killer.getLocation().getDirection());
								if(q > 0 ) {
									event.setDamage(21);
									killer.setHealth(killer.getHealth() + 6);
								}
								else event.setDamage(11);
							
							} else event.setCancelled(true);
							
						} else event.setCancelled(true);

					} else event.setCancelled(true);

				} else event.setCancelled(true);
							
			
				
			} else event.setCancelled(true);
			
		} else event.setCancelled(true);	
	}
}