package at.dafnik.ragemode.Weapons;

import org.bukkit.Bukkit;
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
				if(arrow.getMetadata("shootedWith").isEmpty()) {
					arrow.setMetadata("shootedWith", new FixedMetadataValue(plugin, "bow"));
					
					if (arrow.getShooter() instanceof Player) {			
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {			
								new Explosion("bow", event.getEntity().getLocation(), ((Player) arrow.getShooter()), plugin);
								
								arrow.remove();
							}
						}, 20);
					}
				}
			} else arrow.remove();
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
								if (!plugin.respawnsafe.contains(victim)) {
									victim.removeMetadata("killedWith", plugin);
									victim.setMetadata("killedWith", new FixedMetadataValue(plugin, "bow"));
									event.setDamage(21);

								} event.setCancelled(true);
								
							} else event.setCancelled(true);
							
						} //Don't add else because grenate is also with shootedWith
						
					} else event.setCancelled(true);
				
					arrow.remove();
					
				} else arrow.remove();
				
			} else event.setCancelled(true);
		}
	}
}
