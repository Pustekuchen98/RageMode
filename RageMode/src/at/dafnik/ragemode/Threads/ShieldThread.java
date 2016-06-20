package at.dafnik.ragemode.Threads;

import at.dafnik.ragemode.Main.Library;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShieldThread implements Runnable{

	private Player player;
	Thread thread;
	private Boolean running = false;
	private int zaehler = 0;

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
			player.getInventory().getItemInOffHand().setDurability((short) (player.getInventory().getItemInOffHand().getDurability() + 15));

			if(zaehler >= 20) {
				player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
				Library.powerup_shield.remove(player);
				this.stop();
			}

			zaehler++;

			try{
				Thread.sleep(1000);
			} catch (InterruptedException e){ this.stop(); }
		}		
	}
}
