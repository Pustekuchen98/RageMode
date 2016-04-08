package at.dafnik.ragemode.Threads;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class CompassThread implements Runnable {
	
	private Thread thread;
	private boolean running;
	
	public CompassThread() {
		this.thread = new Thread(this);
		
		System.out.println("ini");
	}
	
	public void start() {
		this.running = true;
		
		if(this.running) this.thread.start();
		
		System.out.println("started");
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		this.running = false;
		
		this.thread.stop();
	}

	@Override
	public void run() {
		while(running) {
			if(Main.status == Status.INGAME) {
				try {
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(!Library.spectatorlist.contains(players)) {
							Player target = getNearest(players);
							if(target != null) players.setCompassTarget(target.getLocation());		
						}
					}
				} catch (Exception ex) {
					System.out.println(Strings.log_pre + "Compass" + Strings.error_thread_exception);
				}
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}	
	}

	private Player getNearest(Player player) {
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
