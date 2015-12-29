package at.dafnik.ragemode.Weapons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.FixedMetadataValue;

import at.dafnik.ragemode.API.Explosion;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Bow implements Listener{
	
	private Main plugin;

	public Bow(Main main){
		this.plugin = main;
	}
	
	double radius = 5;
	
	@EventHandler
	public void HitEvent(ProjectileHitEvent event){	
		if (event.getEntity() instanceof Arrow && event.getEntity().getShooter() instanceof Player) {
			
			Arrow arrow = (Arrow) event.getEntity();
			
			if(Main.status == Status.INGAME) {
				arrow.setMetadata("shootedWith", new FixedMetadataValue(plugin, "bow"));
				
				if (arrow.getShooter() instanceof Player) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							Location loc = event.getEntity().getLocation();
							
							new Explosion("bow", loc, ((Player) arrow.getShooter()), plugin);
							
							arrow.remove();
						}
					}, 20);
				}
			} else {
				arrow.remove();
			}
		}
	}
		
	@EventHandler
	public void DamagebyEntity(EntityDamageByEntityEvent event){	
		if (event.getCause() == DamageCause.PROJECTILE) {
			if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
				Arrow arrow = (Arrow) event.getDamager();
				if(Main.status == Status.INGAME) {
					String shootedWith = arrow.getMetadata("shootedWith").get(0).asString();  
							
					if(shootedWith != null) {
						if(shootedWith == "bow") {	
							if(arrow.getShooter() instanceof Player) {
								Player victim = (Player) event.getEntity();
								if(!plugin.respawnsafe.contains(victim)) {
									plugin.killGroundremover(victim);
									plugin.playerbow.put(victim, (Player) arrow.getShooter());
									plugin.playerbowlist.add(victim);
													
									event.setDamage(21);
								}
							}	
						}			
					}
					arrow.remove();
				} else {
					arrow.remove();
				}
			}
		}
	}
}
