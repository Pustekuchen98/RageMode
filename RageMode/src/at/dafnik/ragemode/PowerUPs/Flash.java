package at.dafnik.ragemode.PowerUPs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Flash implements Listener{
	
	private Main plugin;
	int duration = 10;
	
	public Flash(Main main) {
		this.plugin = main;
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent event) {
		if(Main.status == Status.INGAME) {
			if(event.getEntity() instanceof Snowball) {
				if(event.getEntity().getShooter() instanceof Player) {
					Snowball snowball = (Snowball) event.getEntity();
					Location loc = snowball.getLocation();
					
					loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX(), loc.getY(), loc.getZ(), 50, 0.3, 0.3, 0.3, 0.3);
					for(Entity entities : snowball.getNearbyEntities(6, 6, 6)) {
						if(entities instanceof Player) {
							Player players = (Player) entities;
							
							players.setGlowing(true);
							players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 2));
								
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {			
									players.setGlowing(false);
								}
							}, duration * 20);
						}
					}
				}
			}
		}
	}
}
