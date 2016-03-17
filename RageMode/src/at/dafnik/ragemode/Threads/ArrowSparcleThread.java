package at.dafnik.ragemode.Threads;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;

public class ArrowSparcleThread implements Runnable {
	
	private Thread thread;
	private boolean running;
	private Arrow item;
	
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
			
			loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX(), loc.getY(), loc.getZ(), 10, 0.05, 0.05, 0.05, 0.05);
			
			try{
				Thread.sleep(10);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			
			if(item.isOnGround()) {
				this.stop();
				item.remove();
			}
		}
	}
}
