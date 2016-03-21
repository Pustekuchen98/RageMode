package at.dafnik.ragemode.Threads;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;

public class ArrowSparcleThread implements Runnable {
	
	private Thread thread;
	private boolean running;
	private Arrow item;
	private int zaehler;
	
	public ArrowSparcleThread(Arrow item){
		this.item = item;
		this.thread = new Thread(this);
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
			Location loc = item.getLocation();
			
			loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX(), loc.getY(), loc.getZ(), 5, 0.02, 0.02, 0.02, 0.02);
			
			try{
				Thread.sleep(5);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			
			zaehler++;
			
			if(item.isOnGround()) {
				this.stop();
			}
			
			if(zaehler > 800) this.stop();
		}
	}
}
