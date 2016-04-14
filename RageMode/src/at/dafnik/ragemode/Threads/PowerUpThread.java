package at.dafnik.ragemode.Threads;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class PowerUpThread implements Runnable{
	Thread thread;
	boolean running;
	int time = 1;
	
	public PowerUpThread() {
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
			if(Main.status == Status.INGAME) {				
				Location loc = TeleportAPI.getRandomPowerUPSpawnLocation();
				
				if(loc != null) {		
					spawnPowerUP(loc);
				} else System.out.println(Strings.error_powerup_last);
			}
			
			try {
				Thread.sleep(time*60*1000);
			} catch (InterruptedException e) {
				this.stop();
			}
		}
	}
	
	public static void spawnPowerUP(Location loc) {
		ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta imd = item.getItemMeta();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				imd.setDisplayName(String.valueOf(Library.powerup_integer));
				Library.powerup_integer++;
				imd.addEnchant(Enchantment.WATER_WORKER, 1, true);
				item.setItemMeta(imd);
				Entity entity = loc.getWorld().dropItem(loc, item);
				entity.setCustomName("PowerUPEntity");
				entity.setVelocity((new Vector(0, 0, 0)));
				
				Holograms holo = new Holograms(entity.getLocation(), "§a§lPowerUP");
				for (Player players : Bukkit.getOnlinePlayers()) holo.display(players);

				Library.powerup_hashmap.put(Integer.valueOf(item.getItemMeta().getDisplayName()), holo);
				Library.powerup_list.add(holo);
				Library.powerup_entity.add(entity);	

				for (int i = 0; i < 10; i++) entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
				entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
				entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 3);
			}
		}, 1);		
	}
}
