package at.dafnik.ragemode.Weapons;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.API.Explosion;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.PowerSystem;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Threads.ArrowSparcleThread;

public class Grenade implements Listener{
	
	double radius = 5;
	private ArrowSparcleThread thread = null;
	
	@EventHandler
	public void onChickenSpawn(EntitySpawnEvent event) {
		if(event.getEntity() instanceof Chicken) {
			if(Main.status == Status.INGAME || Main.status == Status.WIN) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHits(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Egg && event.getEntity().getShooter() instanceof Player) {
			Egg egg = (Egg) event.getEntity();

			if(Main.status == Status.INGAME) {
				Location locegg = egg.getLocation();

				Entity eggy = locegg.getWorld().dropItem(locegg, new ItemStack(Material.EGG));
                eggy.setCustomName("GRENADE");
				Player killer = (Player) egg.getShooter();
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),new Runnable() {
					@Override
					public void run() {	
						Location loceggy = eggy.getLocation();
							
						loceggy.getWorld().playEffect(loceggy, Effect.EXPLOSION_HUGE, 1);
						loceggy.getWorld().playSound(loceggy, Sound.ENTITY_GENERIC_EXPLODE, 1000.0F, 1.0F);
						eggy.remove();
		
						int x;
						int y;
						int z;
		
						int rand1;
						int rand2;
		
						Random r = new Random();
		
						for (int i = 0; i < 25; i++) {
		
							rand1 = r.nextInt(2);
							rand2 = r.nextInt(2);
		
							x = r.nextInt(10);
							y = r.nextInt(10);
							z = r.nextInt(10);
		
							if (rand1 == 1) {
								z *= -1;
							}
								
							if (rand2 == 1) {
								x *= -1;
							}
		
							Arrow arrow = loceggy.getWorld().spawnArrow(loceggy, new Vector(x, y, z), 1.0F, 0.3F);
								
							arrow.setFireTicks(10000);						
							arrow.setMetadata("shootedWith", new FixedMetadataValue(Main.getInstance(), "grenade"));
							//arrow.setMetadata("shooter", new FixedMetadataValue(Main.getInstance(), killer.getName()));
							arrow.setShooter(killer);
							
							if(PowerSystem.getPower(killer) > 0) {
								if(Library.sparcleswitch.contains(killer)) new ArrowSparcleThread(arrow).start(); 
							} else arrow.setCritical(true);
		
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),new Runnable() {
								@Override
								public void run() {										
									new Explosion("grenade", arrow.getLocation(), killer);
									
									arrow.remove();
									if(thread != null) thread.stop();
								}
							}, 45);
						}
					}
				}, 45);
			} else {
				egg.remove();
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
						if(shootedWith == "grenade") {	
							if(arrow.getShooter() instanceof Player) {
								Player victim = (Player) event.getEntity();
								if (!Library.respawnsafe.contains(victim)) {
									victim.removeMetadata("killedWith", Main.getInstance());
									victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "grenade"));
									event.setDamage(21);

								} event.setCancelled(true);
								
							} else event.setCancelled(true);
							
						} //Don't add else because bow is also with shootedWith
						
					} else event.setCancelled(true);
				
					arrow.remove();
					
				} else arrow.remove();
				
			} else event.setCancelled(true);
		}
	}
}
