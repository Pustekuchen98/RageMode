package at.dafnik.ragemode.PowerUPs;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.FixedMetadataValue;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class ClayMoreThread implements Runnable{
	
	double radius;
	Player setter;
	Block block;
	Thread thread;
	boolean running;
	
	public ClayMoreThread(Player setter, double radius, Block block) {
		this.radius = radius;
		this.setter = setter;
		this.block = block;
		
		this.thread = new Thread(this);
	}
	
	public void start() {
		this.running = true;
		if(running) {
			this.thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop(){
		this.running = false;
		this.thread.stop();
	}
	
	@Override
	public void run() {
		while(running) {
			for(Entity entity : block.getLocation().getWorld().getNearbyEntities(block.getLocation(), radius, radius, radius)) {
				if(entity instanceof Player) {
					Player victim = (Player) entity;
						
					if(victim != setter) {	
						if(Main.status == Status.INGAME) {
							if(!Library.spectatorlist.contains(victim)) {
								if(!Library.respawnsafe.contains(victim)) {
									Location loc = block.getLocation();
									
									Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
										@SuppressWarnings("deprecation")
										@Override
										public void run() {
											victim.removeMetadata("killedWith", Main.getInstance());
											victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "claymore"));
											victim.damage(11, setter);
											victim.setLastDamageCause(new EntityDamageEvent(setter, DamageCause.PROJECTILE, 0));
											
											loc.getBlock().setType(Material.AIR);
											loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 1);
											loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1000.0F, 1.0F);
										}
									}, 1);							
									this.stop();
								}
							}
						}
					}
				}
			}
		
			if(block == null){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						block.getLocation().getBlock().setType(Material.AIR);
					}
				}, 1);
				this.stop();
			}
			
			try{
				Thread.sleep(5);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
}
