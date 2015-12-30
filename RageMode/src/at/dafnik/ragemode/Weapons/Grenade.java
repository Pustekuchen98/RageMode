package at.dafnik.ragemode.Weapons;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.API.Explosion;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Grenade implements Listener{
	
	private Main plugin;
	double radius = 5;
	
	public Grenade(Main main) {
		this.plugin = main;
	}
	
	@EventHandler
	public void onHits(ProjectileHitEvent event) {
		
		if (event.getEntity() instanceof Egg && event.getEntity().getShooter() instanceof Player) {

			Egg egg = (Egg) event.getEntity();

			if (egg.getShooter() instanceof Player) {
				if(Main.status == Status.INGAME) {
					Location locegg = egg.getLocation();
					
					ItemStack eggi = new ItemStack(Material.EGG);
					Entity eggy = locegg.getWorld().dropItem(locegg, eggi);
					Player killer = (Player) egg.getShooter();
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,new Runnable() {
						@Override
						public void run() {
								
							Location loceggy = eggy.getLocation();
								
							loceggy.getWorld().playEffect(loceggy, Effect.EXPLOSION_HUGE, 1);
							loceggy.getWorld().playSound(loceggy, Sound.EXPLODE, 1000.0F, 1.0F);
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
								arrow.setCritical(true);						
								arrow.setMetadata("shootedWith", new FixedMetadataValue(plugin, "grenade"));
								arrow.setMetadata("shooter", new FixedMetadataValue(plugin, killer.getName()));
	
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,new Runnable() {
									@Override
									public void run() {
										
										Location loc = arrow.getLocation();
										
										new Explosion("grenade", loc, killer, plugin);
										
										arrow.remove();
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
	}
	
	@EventHandler
	public void DamagebyEntity(EntityDamageByEntityEvent event){
		if (event.getCause() == DamageCause.PROJECTILE) {	
			if (event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(Main.status == Status.INGAME) {
					String shootedWith = arrow.getMetadata("shootedWith").get(0).asString(); 
					
					if(shootedWith != null) {
						if(shootedWith == "grenade") {
							String killername = arrow.getMetadata("shooter").get(0).asString();  
						    Player killer = Bukkit.getServer().getPlayer(killername);
							Player victim = (Player) event.getEntity();
							if(!plugin.respawnsafe.contains(victim)) {
								plugin.killGroundremover(victim);
								plugin.playergrenadelist.add(victim);
								plugin.playergrenade.put(victim, killer);
								
								event.setDamage(21.0);
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
