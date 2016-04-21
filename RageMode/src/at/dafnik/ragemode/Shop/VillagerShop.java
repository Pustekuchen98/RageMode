package at.dafnik.ragemode.Shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;

public class VillagerShop implements Runnable {
	
	private Thread thread;
	private boolean running;
	private Villager villager;
	private Location loc;
	
	public VillagerShop() {
		this.thread = new Thread(this);
	}
	
	@Override
	public void run() {
		while(running) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					if(villager.getLocation() != loc) villager.teleport(loc);
					
					if(villager.isDead() || villager == null) stop();
				}
			}, 1);
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						villager.remove();
						stop();
					}
				}, 1);
			}
		}	
	}
	
	public Villager getVillager() { return villager; }
	
	public void start() {
		this.running = true;
		if(running) {
			this.loc = TeleportAPI.getVillagerShopLocation();
			
			this.villager= (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			Library.villagerholo = new Holograms(new Location(loc.getWorld(), loc.getX(), loc.getY() + 1.7, loc.getZ()),
					"§6Shop");
			this.villager.setProfession(Profession.BLACKSMITH);
			this.villager.setRemoveWhenFarAway(false);
			this.villager.setCanPickupItems(false);
			this.villager.setCollidable(false);
			
			this.thread.start();			
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		this.running = false;
		this.thread.stop();
		
		this.villager.remove();
		if(Library.villagerholo != null) {
			for(Player players : Bukkit.getOnlinePlayers()) Library.villagerholo.destroy(players);
		}
		
		if(Bukkit.getOnlinePlayers().size() == 0) {
			Location loc = TeleportAPI.getVillagerShopLocation();
			loc.getWorld().getBlockAt(loc).setType(Material.LAVA);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {			
					loc.getWorld().getBlockAt(loc).setType(Material.AIR);
				}
			}, 30);
		}
	}
}
