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
import org.bukkit.event.block.BlockPlaceEvent;
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
	
	@EventHandler
	public void BlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(Main.status == Status.INGAME) {
			if(event.getBlockReplacedState().getType() == Material.AIR) {
				if(player.getInventory().getItemInMainHand().getType() == Material.STONE_PLATE) {
					event.setCancelled(false);	
					block.setMetadata("placedBy", new FixedMetadataValue(Main.getInstance(), player.getName()));	
					configset(block);
						
				} else if(player.getInventory().getItemInMainHand().getType() == Material.FLOWER_POT_ITEM) {	
					event.setCancelled(false);
					new ClayMoreThread(player, 2F, block).start();		
					configset(block);
					
				} else event.setCancelled(true);
				
			} else event.setCancelled(true);
			
		} else if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && Library.builder.contains(player)
				&& player.hasPermission("ragemode.admin")) event.setCancelled(false);
		
		else event.setCancelled(true);
	}
	
	private void configset(Block block) {
		Library.planted.add(block.getLocation());
		
		String w = block.getWorld().getName();
		int x = block.getLocation().getBlockX();
		int y = block.getLocation().getBlockY();
		int z = block.getLocation().getBlockZ();
		
		int settetnumber;
		int numbertoset = Main.getInstance().getConfig().getInt("ragemode.placedblocks.number");
		settetnumber = numbertoset;
		numbertoset++;
		Main.getInstance().getConfig().set("ragemode.placedblocks.number", Integer.valueOf(numbertoset));
		
		Main.getInstance().getConfig().set("ragemode.placedblocks." + settetnumber + ".world", w);
		Main.getInstance().getConfig().set("ragemode.placedblocks." + settetnumber + ".x", Integer.valueOf(x));
		Main.getInstance().getConfig().set("ragemode.placedblocks." + settetnumber + ".y", Integer.valueOf(y));
		Main.getInstance().getConfig().set("ragemode.placedblocks." + settetnumber + ".z", Integer.valueOf(z));
		Main.getInstance().saveConfig();
	}
}
