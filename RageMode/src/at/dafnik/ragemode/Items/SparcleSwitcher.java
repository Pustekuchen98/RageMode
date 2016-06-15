package at.dafnik.ragemode.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.PowerSystem;
import at.dafnik.ragemode.Main.Main.Status;

public class SparcleSwitcher implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
				if(event.getItem().getType() == Material.NETHER_STAR) {
					Player player = event.getPlayer();
					
					if(PowerSystem.getPower(player) > 0) {
						if(Library.sparcleswitch.contains(player)) {
							Library.sparcleswitch.remove(player);
							
							player.getInventory().remove(event.getItem());
							Items.givePlayerSparcleSwitcher(player, "§cOFF");
						} else {
							Library.sparcleswitch.add(player);
							
							player.getInventory().remove(event.getItem());
							Items.givePlayerSparcleSwitcher(player, "§aON");
						}
					}
				}
			}
		}
	}
}
