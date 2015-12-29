package at.dafnik.ragemode.PowerUPs;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class PowerUpper implements Runnable{
	Thread thread;
	boolean running;
	Main plugin;
	int time = 30;
	
	public PowerUpper(Main main) {
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
			if(Main.status == Status.INGAME) {
				String mapname = plugin.votedmap;		
				int spawnnumber = plugin.getConfig().getInt("ragemode.powerupspawn." + mapname + ".spawnnumber");
	
				if(mapname != null && spawnnumber != 0) {
					Random random = new Random();
					int zahl = random.nextInt(spawnnumber);
					
					for(int i = 0; i < 20; i++) {
						String w = plugin.getConfig().getString("ragemode.powerupspawn." + mapname + "." + zahl + ".world");
						double x = plugin.getConfig().getDouble("ragemode.powerupspawn." + mapname + "." + zahl + ".x");
						double y = plugin.getConfig().getDouble("ragemode.powerupspawn." + mapname + "." + zahl + ".y");
						double z = plugin.getConfig().getDouble("ragemode.powerupspawn." + mapname + "." + zahl + ".z");
						
						if(w != null) {
							Location loc = new Location(Bukkit.getWorld(w), x, y, z);
							
							ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
							ItemMeta imd = item.getItemMeta();
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									imd.setDisplayName(String.valueOf(Main.powerupinteger));
									Main.powerupinteger++;
									imd.addEnchant(Enchantment.WATER_WORKER, 1, true);
									item.setItemMeta(imd);
									Entity entity = loc.getWorld().dropItem(loc, item);
									
									Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										@Override
										public void run() {
											Holograms holo = new Holograms(entity.getLocation(), "§a§lPowerUP");
											for (Player players : Bukkit.getOnlinePlayers()) holo.display(players);
											
											plugin.poweruphashmap.put(Integer.valueOf(item.getItemMeta().getDisplayName()), holo);
											plugin.powerupentity.add(entity);
										}
									}, 20);
	
									for (int i = 0; i < 10; i++) entity.getWorld().playEffect(entity.getLocation(), Effect.LAVA_POP, 3);
									entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
									entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 3);
								}
							}, 1);
	
							if (Main.isDebug) System.out.print("[Debug]> PowerUP spawnt on: " + zahl + " | Coordinates: " + w + ", " + x	+ " ," + y + ", " + z);
							break;
							
						} else System.out.println("[RageMode] ERROR: PowerUP spawing failed! Caused by: Coordinates Error. Trying Spawn function again.");
					}	
				} else System.out.println("[RageMode] ERROR: PowerUP spawning failed! Caused by: No Map or no PowerUP Spawns");
			}
			
			try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
