package at.dafnik.ragemode.Threads;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;

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
	
	public static Villager spawnVillagerShop() {
		Location loc = TeleportAPI.getVillagerShopLocation();
		
		if(loc != null) {
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			Library.villagerholo = new Holograms(new Location(loc.getWorld(), loc.getX(), loc.getY()+1.7, loc.getZ()), "§6Shop");
			villager.setProfession(Profession.BLACKSMITH);
			villager.setRemoveWhenFarAway(false);
			villager.setCanPickupItems(false);
			new VillagerThread(villager, loc).start();
			return villager;
		} else {
			System.out.println(Strings.error_not_existing_villagerspawn);
			return null;
		}
	}
	
	public static void deleteVillagerShop() {
		if(Library.villager != null) {
			Library.villager.remove();
			Library.villager = null;
			
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
}
