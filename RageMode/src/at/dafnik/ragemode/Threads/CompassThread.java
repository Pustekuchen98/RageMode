package at.dafnik.ragemode.Threads;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class CompassThread implements Runnable {
	Thread thread;
	boolean running;
	
	public CompassThread(Main main) {
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
			if(Main.status == Status.INGAME) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					Player target = getNearest(player);
					if(target != null) {
						player.setCompassTarget(target.getLocation());
					}
				}
			}
			
			try{
				Thread.sleep(500);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}	
	}
	
	public Player getNearest(Player player) {
		double distance = Double.MAX_VALUE;
		Player target = null;
		
		if(!player.getNearbyEntities(3000, 3000, 3000).isEmpty()) {
			for(Entity entity : player.getNearbyEntities(3000, 3000, 3000)) {
				if(entity != null) {
					if(entity instanceof Player) {
						if(!entity.isDead()) {
							Player gettet = (Player) entity;
							if(!(Library.spectatorlist.contains(gettet))) {
								double dis = player.getLocation().distance(entity.getLocation());
								if(dis < distance) {
									distance = dis;
									target = (Player) entity;
								}
							}
						}
					}
				}
			}
		}
		return target;
	}
}
