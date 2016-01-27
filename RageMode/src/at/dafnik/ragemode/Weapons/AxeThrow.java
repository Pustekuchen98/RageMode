package at.dafnik.ragemode.Weapons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class AxeThrow implements Runnable{
	
	private Thread thread;
	private Player player;
	private double radius;
	private boolean running;
	private Item item;
	
	private Main plugin;
	
	public AxeThrow(Player player, double radius, Item item, Main main){
		this.player = player;
		this.radius = radius;
		this.item = item;
		this.thread = new Thread(this);
		
		this.plugin = main;
	}
	
	public void start(){
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
		while(running){
			for(Entity entity : item.getNearbyEntities(radius, radius, radius)){
				if(entity instanceof Player){
					Player victim = (Player) entity;
					
					if(victim != player){
						if(Main.status == Status.INGAME) {
							if(!plugin.spectatorlist.contains(victim)) {
								if(!plugin.respawnsafe.contains(victim)) {

							        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										@Override
										public void run() {
											victim.removeMetadata("killedWith", plugin);
											victim.setMetadata("killedWith", new FixedMetadataValue(plugin, "combataxe"));
											victim.damage(41, player);
										}
									}, 1);
									
									this.item.remove();
									this.stop();
								}
							}
						}
					}
				}
			}

			if(item.isOnGround()){
				this.item.remove();
				this.stop();
			}
			if(item == null){
				this.item.remove();
				this.stop();
			}
			try{
				Thread.sleep(10);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
