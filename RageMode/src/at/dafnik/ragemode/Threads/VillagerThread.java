package at.dafnik.ragemode.Threads;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

import at.dafnik.ragemode.API.Holograms;

public class VillagerThread implements Runnable{
	
	Thread thread;
	boolean running;
	Villager villager;
	Location loc;
	Holograms holo;
	
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
			
			if(villager.isDead() || villager == null) this.stop();
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				this.stop();
			}
		}
	}
}
