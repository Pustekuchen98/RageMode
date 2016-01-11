package at.dafnik.ragemode.Main;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Items.CompassThread;
import at.dafnik.ragemode.PowerUPs.PowerUpper;
import at.dafnik.ragemode.Weapons.KnifeThread;

public class Saver implements Runnable{
	
	Thread thread;
	boolean running;
	Main plugin;
	CompassThread ct;
	KnifeThread kt;
	PowerUpper pu;
	
	int zaehler = 0;
	
	public Saver(Main main) {
		this.plugin = main;
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
			
			kt = new KnifeThread(plugin);
			kt.start();
			ct = new CompassThread(plugin);
			ct.start();
			
			if(Main.isDebug) {
				if(zaehler >= 30) System.out.println(Strings.debug_saver_started);
			}
			
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.kt.stop();
			this.ct.stop();
			
			if(Main.isDebug) {
				if(zaehler >= 30) {
					System.out.println(Strings.debug_saver_stopped);
					zaehler = 0;
				}
				zaehler++;
			}
		}
	}
}
