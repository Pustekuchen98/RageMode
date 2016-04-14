package at.dafnik.ragemode.Threads;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;

public class FlyThread implements Runnable{

	private Player player;
	Thread thread;
	private Boolean running = false;
	
	public FlyThread(Player player) {
		this.player = player;
		
		this.thread = new Thread(this);
	}
	
	public void start() {
		running = true;
		if(running) {
			this.thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		running = false;
		this.thread.stop();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while(running) {
			try{
				Thread.sleep(500);
			}catch (InterruptedException e){
				this.stop();
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {	
					if(player.isOnGround()) {
						Library.powerup_flyparticle.remove(player);
						player.getInventory().setChestplate(null);
						stop();
					}
				}
			}, 1);
		}		
	}
}
