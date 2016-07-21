package at.dafnik.ragemode.Threads;

import at.dafnik.ragemode.Main.Library;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShieldThread implements Runnable{

	private Player player;
	private Thread thread;
	private Boolean running = false;

	public ShieldThread(Player player) {
		this.player = player;
		
		this.thread = new Thread(this);
	}
	
	public void start() {
		running = true;
		this.thread.start();
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
				Thread.sleep(20000);
			} catch (InterruptedException e){ this.stop(); }

			player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
			Library.powerup_shield.remove(player);
			this.stop();
		}		
	}
}
