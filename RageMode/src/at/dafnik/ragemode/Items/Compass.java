package at.dafnik.ragemode.Items;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.generator.InternalChunkGenerator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;

import java.util.HashMap;

public class Compass implements Listener{
	
	private double distance = Double.MAX_VALUE;

    private HashMap<Player, Integer> foreveralonecounter = new HashMap<Player, Integer>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
			Player target = getNearest(player);
			if(target == null) {
				player.sendMessage(Strings.item_compass_error);

                if(foreveralonecounter.get(player) == null) {
                    foreveralonecounter.put(player, 1);
                } else {
                    foreveralonecounter.replace(player, foreveralonecounter.get(player) + 1);
                }

                if(foreveralonecounter.get(player) > 10) {
                    foreveralonecounter.replace(player, 0);
                    player.sendMessage(Strings.item_compass_error_4ever_alone);
                }

				return;
			}
			
			int blocks = 0;
			try {
				blocks = (int) player.getLocation().distance(target.getLocation());
			} catch (Exception ex){
				System.out.println(Strings.error_cast_to_int);
				blocks = -1;
			}
			if(blocks != -1) player.sendMessage(Strings.item_compass_1 + target.getDisplayName() + Strings.item_compass_2 + blocks);
		}
	}
	
	public Player getNearest(Player player) {
		double distance = Double.MAX_VALUE;
		Player target = null;
		
		if(!player.getNearbyEntities(3000,  3000,  3000).isEmpty()) {
			for(Entity entity : player.getNearbyEntities(3000, 3000, 3000)) {
				if(entity instanceof Player) {
					Player gettet = (Player) entity;
					if(!(Library.spectatorlist.contains(gettet))) {
						double dis = player.getLocation().distance(entity.getLocation());
						if(dis < distance) {
							distance = dis;
							target = (Player) entity;
						}
					}
				}
			}
		}
		return target;
	}
}
