package at.dafnik.ragemode.PowerUPs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class ClayMoreThread implements Runnable{
	
	double radius;
	Player setter;
	Block block;
	Thread thread;
	boolean running;
	
	private Main plugin;
	
	public ClayMoreThread(Player setter, double radius, Block block, Main main) {
		this.radius = radius;
		this.setter = setter;
		this.block = block;
		
		this.thread = new Thread(this);
		
		this.plugin = main;
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
							if(!plugin.spectatorlist.contains(victim)) {
								if(!plugin.respawnsafe.contains(victim)) {
									Location loc = block.getLocation();
									
									plugin.killGroundremover(victim);
									plugin.playerclaymore.put(victim, setter);
									plugin.playerclaymorelist.add(victim);
									
									Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										@Override
										public void run() {
											loc.getBlock().setType(Material.AIR);
	
											victim.damage(11);
											
											loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 3.0F, false, false);	
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
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {

						block.getLocation().getBlock().setType(Material.AIR);
					}
				}, 5);
				
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
