package at.dafnik.ragemode.Main;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class VillagerThread implements Runnable{
	
	Thread thread;
	boolean running;
	Villager villager;
	Location loc;
	
	public VillagerThread(Villager villager, Location loc) {
		this.villager = villager;
		this.loc = loc;
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
			if(villager.getLocation() != loc) villager.teleport(loc);
	
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
