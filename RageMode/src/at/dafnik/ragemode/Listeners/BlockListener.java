package at.dafnik.ragemode.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import at.dafnik.ragemode.API.C4Speicher;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Threads.ClayMoreThread;

public class BlockListener implements Listener {
	
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
					
				} else if(player.getInventory().getItemInMainHand().getType() == Material.STONE_BUTTON) {
					event.setCancelled(false);
					for(C4Speicher c4s : Library.plantedc4) {
						if(c4s.getPlayer() == player) {
							c4s.addC4(block);
							
							break;
						} else {
							C4Speicher c4n = new C4Speicher();
							c4n.setPlayer(player);
							c4n.addC4(block);
							
							Library.plantedc4.add(c4n);
							break;
						}
					}
					
					if(Library.plantedc4.size() == 0) {
						C4Speicher c4n = new C4Speicher();
						c4n.setPlayer(player);
						c4n.addC4(block);
						
						Library.plantedc4.add(c4n);
					}
					
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
