package at.dafnik.ragemode.PowerUPs;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import net.minecraft.server.v1_10_R1.MathHelper;

public class DoubleJump implements Listener {
	
	private HashMap<Player, Boolean> cooldown = new HashMap<Player, Boolean>();
	private ArrayList<Player> smash = new ArrayList<Player>();
    int radius = 5;
    	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void EntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if(Library.powerup_doublejump.contains(player)) {
				if (smash.contains(player)) {
					ArrayList<Block> blocks = new ArrayList<Block>();
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(1, 1, 0)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 1)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(-1, 1, 0)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, -1)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(1, 1, 1)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(-1, 1, -1)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(2, 1, 0)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(-2, 1, 0)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 2)));
					blocks.add(player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, -2)));
						
					for (Block b : blocks) {
		                for (Player pl : Bukkit.getOnlinePlayers()) {
		                	pl.playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId());
		                }
					}
					
					Location loc = player.getLocation();
					
					for (Entity entities : player.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
						if (entities instanceof Player) {
							if(((Player) entities) != player) {
								if(!(Library.spectatorlist.contains(entities) && Library.respawnsafe.contains(player))) {
									Location eloc = entities.getLocation();
									
									double d4 = eloc.distance(loc) / radius;
	
									if (d4 <= 1.0D) {
										double d5 = eloc.getBlockX() - loc.getBlockX();
										double d6 = eloc.getBlockY() + (double) ((Player) entities).getEyeHeight() - loc.getBlockY();
										double d7 = eloc.getBlockZ() - loc.getBlockZ();
										double d9 = (double) MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
	
										if (d9 != 0.0D) {
											d5 /= d9;
											d6 /= d9;
											d7 /= d9;
	
											Vector vector = new Vector(d5,d6, d7).normalize();
											((Player) entities).setVelocity(vector);
											((Player) entities).damage(1);
										}
									}	
								}
							}
						}
					}
				}
				smash.remove(player);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if(Library.powerup_doublejump.contains(player)) {
			if (player.getGameMode() == GameMode.CREATIVE)
				return;
	
			if (cooldown.get(player) != null && cooldown.get(player) == true) {
				player.setAllowFlight(true);
			} else {
				player.setAllowFlight(false);
			}
	
			if (player.isOnGround()) {
				cooldown.put(player, true);
			}
		} else if(Main.status == Status.INGAME) player.setAllowFlight(false);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		
		if(Library.powerup_doublejump.contains(player)) {
			if (player.getGameMode() == GameMode.CREATIVE) return;
			
			if (cooldown.get(player) == true) {
				event.setCancelled(true);
				cooldown.put(player, false);
				player.setVelocity(player.getLocation().getDirection().multiply(1.0D).setY(0.9D));
				
				player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 10000, 10);
				
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				}
	
				player.setAllowFlight(false);
			}
		} else if(Main.status == Status.INGAME) player.setAllowFlight(false);
		
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		
		if(Library.powerup_doublejump.contains(player)) {
			if (player.getGameMode() == GameMode.CREATIVE)
				return;
	
			if (player.isOnGround() == false && cooldown.get(player) != null && cooldown.get(player) == false) {
				if(!smash.contains(player)) smash.add(player);
				player.setVelocity(new Vector(0, -2, 0));
			}
		}
	}
}
