package at.dafnik.ragemode.PowerUPs;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Mine implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		Player victim = event.getPlayer();
			
		if (victim.getLocation().subtract(0.0D, 0.0D, 0.0D).getBlock().getType() == Material.STONE_PLATE) {
			Location loc = victim.getLocation();
			Block block = victim.getLocation().subtract(0.0D, 0.0D, 0.0D).getBlock();
			
			if(Library.planted.contains(block.getLocation())) {
				if(!block.getMetadata("placedBy").isEmpty()) {
					if(!Library.respawnsafe.contains(victim)) {
						String killername = block.getMetadata("placedBy").get(0).asString();  
					    Player killer = Bukkit.getServer().getPlayer(killername);
					    
						if(!(victim == killer)) {
							if(Main.status == Status.INGAME) {				    
								victim.removeMetadata("killedWith", Main.getInstance());
								victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "mine"));
								
								victim.damage(21, killer);
								victim.setLastDamageCause(new EntityDamageEvent(killer, DamageCause.PROJECTILE, 0));
								
								loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 1);
								loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1000.0F, 1.0F);
								loc.getBlock().setType(Material.AIR);
								Library.planted.remove(block.getLocation());													    
							}
						}
					}
				}
			}
		}
	}
}
