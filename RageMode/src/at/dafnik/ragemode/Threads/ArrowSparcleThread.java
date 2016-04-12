package at.dafnik.ragemode.Threads;

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
			item.getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, item.getLocation().getX(), item.getLocation().getY(), item.getLocation().getZ(), 5, 0.02, 0.02, 0.02, 0.02);			
			zaehler++;
			
			if(item.isOnGround() || item.isDead() || item == null) this.stop();
			if(zaehler > 800) this.stop();
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				this.stop();
			}
		}
	}
}
