package at.dafnik.ragemode.PowerUPs;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Threads.FlyThread;

public class Fly implements Listener{
	
	private int duration = 3;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(Main.status == Status.INGAME) {
			if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
					|| event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(event.getItem() != null) {
					if(event.getItem().getType() == Material.NETHER_STAR) {
						Player player = event.getPlayer();
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, duration * 20, 15));
						player.getInventory().setChestplate(new ItemStack(Material.ELYTRA, 1));
						player.getInventory().remove(event.getItem());
						Library.powerup_flyparticle.add(player);
						
						new FlyThread(player).start();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(Main.status == Status.INGAME) {
			Player player = event.getPlayer();
			Location loc = player.getLocation();
			
			if(Library.powerup_flyparticle.contains(player)) {
				Random random = new Random();
				int irand = random.nextInt(10);
				
				if(0 < irand && irand < 3) {
					loc.getWorld().spawnParticle(Particle.FLAME, loc.getX(), loc.getY(), loc.getZ(), 10, 0.05, 0.05, 0.05, 0.05);
				} else if(3 <= irand && irand < 9) {
					loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc.getX(), loc.getY(), loc.getZ(), 20, 0.05, 0.05, 0.05, 0.05);
				} else {
					loc.getWorld().spawnParticle(Particle.LAVA, loc.getX(), loc.getY(), loc.getZ(), 5, 0.05, 0.05, 0.05, 0.05);
				}		
			}
		}
	}
}
