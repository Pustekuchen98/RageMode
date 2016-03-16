package at.dafnik.ragemode.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.Main.Main;

public class OwnFireworkThread implements Runnable {

	private Thread thread;
	private Player player;
	private boolean running;
	private Item item;
	private List<Entity> sparclelist = new ArrayList<Entity>();
	
	public OwnFireworkThread(Player player, Item item){
		this.player = player;
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
			
			try{
				Thread.sleep(2*1000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			
			Location loc = item.getLocation();
			
			item.remove();	

			Random r = new Random();
			
			for (int i = 0; i < 100; i++) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),new Runnable() {
					@Override
					public void run() {				
						int x;
						int y;
						int z;

						int rand1;
						int rand2;
						
						rand1 = r.nextInt(2);
						rand2 = r.nextInt(2);
		
						x = r.nextInt(1) + 5;
						y = r.nextInt(1) + 5;
						z = r.nextInt(1) + 5;
						
						double klein = 0.2;
						
						x *= klein;
						y *= klein;
						z *= klein;
		
						if (rand1 == 1) {
							z *= -1;
						}
							
						if (rand2 == 1) {
							x *= -1;
						}
		
								Entity entity = loc.getWorld().dropItem(loc, new ItemStack(Material.EGG));
								entity.setVelocity(new Vector(x, y, z));
								//((Egg) entity).setShooter(player);
								sparclelist.add(entity);							
														
					}
				}, 1);
				
				try{
					Thread.sleep(200);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			
			try{
				Thread.sleep(3*1000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			
			for(Entity entites : sparclelist) entites.remove();
			
			this.stop();
		}
	}	
}
