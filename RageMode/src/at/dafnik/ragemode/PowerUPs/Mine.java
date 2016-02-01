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

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Mine implements Listener{
	
	private Main plugin;
	
	public Mine(Main main){
		this.plugin = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		Player victim = event.getPlayer();
			
		if (victim.getLocation().subtract(0.0D, 0.0D, 0.0D).getBlock().getType() == Material.STONE_PLATE) {
			Location loc = victim.getLocation();
			Block block = victim.getLocation().subtract(0.0D, 0.0D, 0.0D).getBlock();
			
			if(plugin.planted.contains(block.getLocation())) {
				if(!block.getMetadata("placedBy").isEmpty()) {
					if(!plugin.respawnsafe.contains(victim)) {
						String killername = block.getMetadata("placedBy").get(0).asString();  
					    Player killer = Bukkit.getServer().getPlayer(killername);
					    
						if(!(victim == killer)) {
							if(Main.status == Status.INGAME) {				    
								victim.removeMetadata("killedWith", plugin);
								victim.setMetadata("killedWith", new FixedMetadataValue(plugin, "mine"));
								
								victim.damage(21, killer);
								victim.setLastDamageCause(new EntityDamageEvent(killer, DamageCause.PROJECTILE, 0));
								
								loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 1);
								loc.getWorld().playSound(loc, Sound.EXPLODE, 1000.0F, 1.0F);
								loc.getBlock().setType(Material.AIR);
								plugin.planted.remove(block.getLocation());													    
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
				if(player.getItemInHand().getType() == Material.STONE_PLATE) {
					event.setCancelled(false);	
					block.setMetadata("placedBy", new FixedMetadataValue(plugin, player.getName()));	
					configset(block);
						
				} else if(player.getItemInHand().getType() == Material.FLOWER_POT_ITEM) {	
					event.setCancelled(false);
					new ClayMoreThread(player, 2F, block, plugin).start();		
					configset(block);
					
				} else event.setCancelled(true);
				
			} else event.setCancelled(true);
			
		} else if ((Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) && plugin.builder.contains(player)
				&& player.hasPermission("ragemode.admin")) event.setCancelled(false);
		
		else event.setCancelled(true);
	}
	
	private void configset(Block block) {
		plugin.planted.add(block.getLocation());
		
		String w = block.getWorld().getName();
		int x = block.getLocation().getBlockX();
		int y = block.getLocation().getBlockY();
		int z = block.getLocation().getBlockZ();
		
		int settetnumber;
		int numbertoset = plugin.getConfig().getInt("ragemode.placedblocks.number");
		settetnumber = numbertoset;
		numbertoset++;
		plugin.getConfig().set("ragemode.placedblocks.number", Integer.valueOf(numbertoset));
		
		plugin.getConfig().set("ragemode.placedblocks." + settetnumber + ".world", w);
		plugin.getConfig().set("ragemode.placedblocks." + settetnumber + ".x", Integer.valueOf(x));
		plugin.getConfig().set("ragemode.placedblocks." + settetnumber + ".y", Integer.valueOf(y));
		plugin.getConfig().set("ragemode.placedblocks." + settetnumber + ".z", Integer.valueOf(z));
		plugin.saveConfig();
	}
}
